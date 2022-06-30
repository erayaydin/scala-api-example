package in.yayd.era.api_example.database

import slick.dbio.DBIO

import scala.concurrent.ExecutionContext

case class DBIOOptional[+A](dbio: DBIO[Option[A]]) extends AnyVal {
  def flatMap[B](f: A => DBIOOptional[B])(implicit ec: ExecutionContext): DBIOOptional[B] = {
    val result: DBIO[Option[B]] = dbio.flatMap {
      case Some(a) => f(a).dbio
      case None    => DBIO.successful(None)
    }
    DBIOOptional(result)
  }

  def map[B](f: A => B)(implicit ec: ExecutionContext): DBIOOptional[B] = {
    DBIOOptional(dbio.map(d => d map f))
  }
}
