package in.yayd.era.api_example

import java.sql.Timestamp

package object users {
  case class User(id: Long,
                  username: String,
                  password: String,
                  email: String,
                  createdAt: Timestamp,
                  updatedAt: Timestamp) {
    require(username.nonEmpty, "username.empty")
    require(email.nonEmpty, "email.empty")
  }

  case class AuthTokenContent(userId: Long)
  case class AuthResponse(username: String, email: String, access_token: String)

  case class AuthRequest(email: String, password: String)
  case class RegisterRequest(username: String, email: String, password: String)
}
