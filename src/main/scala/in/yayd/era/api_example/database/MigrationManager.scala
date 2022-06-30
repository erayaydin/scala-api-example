package in.yayd.era.api_example.database

import org.flywaydb.core.Flyway
import org.flywaydb.core.api.output.MigrateOutput
import scala.jdk.CollectionConverters._

class MigrationManager (jdbcURL: String, username: String, password: String) {
  val flyway: Flyway = Flyway.configure().dataSource(jdbcURL, username, password).load()

  def migrate(): Iterable[String] = {
    val steps = flyway.migrate()
    if (steps.migrationsExecuted > 0) {
      steps.migrations.asScala.map(_.filepath).asInstanceOf[Iterable[String]]
    } else {
      Seq.empty.asInstanceOf[Iterable[String]]
    }
  }
  def drop(): Unit = flyway.clean()
}
