package spray.workshop

import akka.actor._

class UsersService extends Actor {
  import UsersService._

  var allUsers: Seq[UserId] = Nil

  class UserImpl(val id: String) extends UserId {
    val ref: ActorRef = context.actorOf(Props(classOf[UserService], this))
  }

  def receive = {
    case CreateUser(id) =>
      val newUser = new UserImpl(id)
      allUsers :+= newUser
      sender ! UserCreated(newUser)

    case GetUsers => sender ! Users(allUsers)
  }
}

object UsersService {
  case object GetUsers
  case class Users(users: Seq[UserId])

  case class CreateUser(id: String)
  case class UserCreated(userId: UserId)
}
