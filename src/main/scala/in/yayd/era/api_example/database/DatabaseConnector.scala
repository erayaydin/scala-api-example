package in.yayd.era.api_example.database

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import slick.jdbc.JdbcBackend.Database

class DatabaseConnector(jdbcURL: String, username: String, password: String, maxConnection: Option[Int] = None) {
  val config = new HikariConfig()
  config.setJdbcUrl(jdbcURL)
  config.setUsername(username)
  config.setPassword(password)

  val db = Database.forDataSource(new HikariDataSource(config), maxConnection)
  db.createSession()
}
