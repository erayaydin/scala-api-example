package in.yayd.era.api_example.users

import in.yayd.era.api_example.database.{DBIOOptional, StorageRunner}
import pdi.jwt.{Jwt, JwtAlgorithm}
import com.roundeights.hasher.Implicits._
import scala.language.postfixOps
import io.circe.syntax._
import io.circe.generic.auto._

import java.sql.Timestamp
import java.util.Date
import scala.concurrent.{ExecutionContext, Future}

class UserService(runner: StorageRunner, userStorage: UserStorage, secretKey: String)(implicit executionContext: ExecutionContext) {

  def login(email: String, password: String): Future[Option[AuthResponse]] =
    runner.run((for {
      user <- DBIOOptional(userStorage.findUserByEmail(email, password.sha256))
    } yield {
      AuthResponse(user.username, user.email, createToken(user.id))
    }).dbio)

  def register(username: String, email: String, password: String): Future[AuthResponse] =
    runner.run(for {
      user <- userStorage.store(User(0, username, password.sha256, email, new Timestamp((new Date).getTime), new Timestamp((new Date).getTime)))
    } yield {
      AuthResponse(user.username, user.email, createToken(user.id))
    })

  private def createToken(id: Long): String = {
    Jwt.encode(AuthTokenContent(id).asJson.noSpaces, secretKey, JwtAlgorithm.HS256)
  }
}
