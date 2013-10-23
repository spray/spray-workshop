package spray.workshop

import akka.actor._

class UserService(userId: UserId) extends Actor {
  import UserService._

  var timeline: Seq[Message] = Nil

  def receive = {
    case PostMessage(message) if message.user == userId =>
      timeline :+= message

    case GetTimeline(`userId`) => sender ! Timeline(timeline)
  }
}

object UserService {

  case class GetTimeline(userId: UserId)
  case class PostMessage(message: Message)
  case class NewMessagePosted(message: Message)
}
