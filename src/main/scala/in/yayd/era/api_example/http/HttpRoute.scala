package in.yayd.era.api_example.http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import in.yayd.era.api_example.users.UserRoute

import scala.concurrent.ExecutionContext

class HttpRoute(userRoute: UserRoute, secretKey: String)(implicit executionContext: ExecutionContext) {
  val route: Route =
    cors() {
      pathPrefix("api") {
        userRoute.route ~
        pathPrefix("healthcheck") {
          get {
            complete("OK")
          }
        }
      }
    }
}
