package spray.workshop

import spray.http.{ Uri, DateTime }
import akka.actor._
import spray.json._

trait UserId {
  def id: String
  //def displayName: String
  def ref: ActorRef

  override def hashCode(): Int = id.hashCode
  override def equals(obj: scala.Any): Boolean = obj match {
    case i: UserId => i.id == id
    case _         => false
  }

  override def toString: String = s"<User:$id>"

  def newMessage(msg: String): Message = Message(this, DateTime.now, msg)
}
case class UserRef(id: String, apiUri: Option[Uri], htmlUri: Option[Uri])

case class Message(user: UserId, time: DateTime, message: String)
case class Timeline(messages: Seq[Message])

trait UserJsonProtocol {
  import spray.json.DefaultJsonProtocol._

  implicit lazy val uriFormat = new JsonFormat[Uri] {
    def write(obj: Uri): JsValue = obj.toString.toJson
    def read(json: JsValue): Uri = Uri(json.convertTo[String])
  }
  implicit lazy val userRefFormat = jsonFormat3(UserRef)

  implicit lazy val userIdFormat: JsonFormat[UserId] = new JsonFormat[UserId] {
    def write(obj: UserId): JsValue = userRef(obj).toJson
    def read(json: JsValue): UserId = resolveUserId(json.convertTo[UserRef].id)
  }

  implicit lazy val datetimeFormat = new JsonFormat[DateTime] {
    def write(obj: DateTime): JsValue = obj.toIsoDateTimeString.toJson
    def read(json: JsValue): DateTime = DateTime.fromIsoDateTimeString(json.convertTo[String]).get
  }
  implicit lazy val messageFormat: RootJsonFormat[Message] = jsonFormat3(Message)
  implicit lazy val timelineFormat = jsonFormat1(Timeline)

  def userRef(user: UserId): UserRef = UserRef(user.id, Some(apiUriFor(user)), Some(htmlUriFor(user)))

  def apiUriFor(user: UserId): Uri
  def htmlUriFor(user: UserId): Uri
  def resolveUserId(id: String): UserId
}
