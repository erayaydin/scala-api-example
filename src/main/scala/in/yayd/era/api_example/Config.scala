package in.yayd.era.api_example

import pureconfig._
import pureconfig.generic.auto._

case class HttpConfig(host: String, port: Int)
case class DatabaseConfig(host: String, port: String, db: String, username: String, password: String)

case class Config(secretKey: String, http: HttpConfig, database: DatabaseConfig)

object Config {

  def load(): Config =
    ConfigSource.default.load[Config] match {
      case Right(config) => config
      case Left(error) => throw new RuntimeException("Cannot load config file " + error)
    }

}
