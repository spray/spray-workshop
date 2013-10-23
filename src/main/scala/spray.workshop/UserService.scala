package spray.workshop

import akka.actor._

class UserService(userId: UserId) extends Actor {
  import UserService._

  def receive = PartialFunction.empty
}

object UserService {

}
