package in.yayd.era.api_example.users

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.concurrent.ExecutionContext
import io.circe.generic.auto._
import io.circe.syntax._

class UserRoute(secretKey: String, service: UserService)(implicit executionContext: ExecutionContext) extends FailFastCirceSupport {
  import service._

  val route: Route = pathPrefix("users") {
    path("login") {
      pathEndOrSingleSlash {
        post {
          entity(as[AuthRequest]) { request =>
            complete(login(request.email, request.password).map {
              case Some(user) => OK -> user.asJson
              case None       => BadRequest -> None.asJson
            })
          }
        }
      }
    } ~
      pathEndOrSingleSlash {
        post {
          entity(as[RegisterRequest]) { request =>
             complete(register(request.username, request.email, request.password).map { user =>
               user.asJson
             })
          }
        }
      }
  }
}
