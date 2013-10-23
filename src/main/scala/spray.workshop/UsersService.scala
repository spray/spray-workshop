package spray.workshop

import akka.actor._
import spray.routing.authentication.UserPass

class UsersService extends Actor {
  import UsersService._

  var allUsers: Seq[UserId] = Nil

  class UserIdImpl(val id: String) extends UserId {
    val ref: ActorRef = context.actorOf(Props(classOf[UserService], this))
  }

  def receive = {
    case CreateUser(id) =>
      val newUser = new UserIdImpl(id)
      allUsers :+= newUser
      sender ! UserCreated(newUser)

    case GetUsers => sender ! Users(allUsers)

    case AuthenticateUser(userPass) =>
      val response = allUsers.find(_.id == userPass.user)

      sender ! response
  }
}

object UsersService {
  case object GetUsers
  case class Users(users: Seq[UserId])

  case class CreateUser(id: String)
  case class UserCreated(userId: UserId)

  case class AuthenticateUser(userPass: UserPass)
}
