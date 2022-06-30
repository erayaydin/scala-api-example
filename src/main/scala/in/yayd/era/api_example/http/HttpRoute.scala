package in.yayd.era.api_example.http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors

import scala.concurrent.ExecutionContext

class HttpRoute (secretKey: String)(implicit executionContext: ExecutionContext) {
  val route: Route =
    cors() {
      pathPrefix("api") {
        pathPrefix("healthcheck") {
          get {
            complete("OK")
          }
        }
      }
    }
}
