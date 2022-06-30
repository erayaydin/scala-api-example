package in.yayd.era.api_example.database

import slick.dbio.DBIO

import scala.concurrent.Future

class StorageRunner(val connector: DatabaseConnector) {
  val db = connector.db

  def run[T](actions: DBIO[T]): Future[T] = db.run(actions)
}
