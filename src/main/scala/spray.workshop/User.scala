package spray.workshop

import spray.http.{ Uri, DateTime }
import akka.actor._
import spray.json._

trait UserId {
  def id: String
  //def displayName: String
  def ref: ActorRef
}
case class UserRef(id: String, apiUri: Option[Uri], htmlUri: Option[Uri])

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

  def userRef(user: UserId): UserRef = UserRef(user.id, Some(apiUriFor(user)), Some(htmlUriFor(user)))

  def apiUriFor(user: UserId): Uri
  def htmlUriFor(user: UserId): Uri
  def resolveUserId(id: String): UserId
}
