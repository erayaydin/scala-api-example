package in.yayd.era.api_example.users

import slick.dbio.DBIO
import slick.lifted.{ProvenShape, TableQuery}
import slick.jdbc.PostgresProfile.api._
import scala.language.postfixOps

import java.sql.Timestamp
import java.util.Date

class UserStorage {

  class Users(tag: Tag) extends Table[User](tag, "users") {
    def now = new Timestamp((new Date).getTime)

    def id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    def username: Rep[String] = column[String]("username")
    def password: Rep[String] = column[String]("password")
    def email: Rep[String] = column[String]("email")
    def createdAt: Rep[Timestamp] = column[Timestamp]("created_at", O.Default(now))
    def updatedAt: Rep[Timestamp] = column[Timestamp]("updated_at", O.Default(now))

    override def * : ProvenShape[User] =
      (id, username, password, email, createdAt, updatedAt) <> ((User.apply _).tupled, User.unapply)
  }

  protected val users = TableQuery[Users]

  def findUserByEmail(email: String, password: String): DBIO[Option[User]] =
    users
      .filter(u => u.email === email && u.password === password)
      .result
      .headOption

  def store(user: User): DBIO[User] =
    (users returning users.map(_.id) into ((u, id) => u.copy(id = id + 1))) += user
}
