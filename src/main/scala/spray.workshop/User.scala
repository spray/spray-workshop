package spray.workshop

import spray.http.DateTime
import akka.actor._
import spray.json._

trait UserId {
  def id: String
  //def displayName: String
  def ref: ActorRef
}

trait UserJsonProtocol {
  import spray.json.DefaultJsonProtocol._

  implicit lazy val userIdFormat: JsonFormat[UserId] = new JsonFormat[UserId] {
    def write(obj: UserId): JsValue = obj.id.toJson
    def read(json: JsValue): UserId = json match {
      case JsString(id) => resolveUserId(id)
    }
  }

  def resolveUserId(id: String): UserId
}
