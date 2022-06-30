package in.yayd.era.api_example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.Materializer
import in.yayd.era.api_example.database.{DatabaseConnector, MigrationManager, StorageRunner}
import in.yayd.era.api_example.http.HttpRoute

import scala.concurrent.ExecutionContext
import scala.io.StdIn
import scala.util.{Failure, Success}

object Boot extends App {
  implicit val system: ActorSystem = ActorSystem("api-example")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: Materializer = Materializer.matFromSystem

  val config = Config.load()
  val jdbcURL = s"jdbc:postgresql://${config.database.host}:${config.database.port}/${config.database.db}"

  val databaseConnector  = new DatabaseConnector(jdbcURL, config.database.username, config.database.password, Some(8))
  val migrationManager   = new MigrationManager(jdbcURL, config.database.username, config.database.password)
  migrationManager.migrate().foreach(migration => println(s"Migrated $migration"))

  val storageRunner = new StorageRunner(databaseConnector)

  val httpRoute = new HttpRoute(config.secretKey)

  val binding = Http()
    .newServerAt(config.http.host, config.http.port)
    .bind(httpRoute.route)
    .andThen {
      case Success(binding) =>
        println(s"Server is running at ${binding.localAddress.getHostName}:${binding.localAddress.getPort}")
      case Failure(err) =>
        err.printStackTrace()
    }

  StdIn.readLine()

  binding
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
