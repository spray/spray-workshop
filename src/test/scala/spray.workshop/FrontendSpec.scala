package spray.workshop

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http._
import StatusCodes._
import akka.actor.ActorRef
import scala.concurrent.ExecutionContext

class FrontendSpec extends Specification with Specs2RouteTest with FrontendService {
  def actorRefFactory = system
  implicit def executionContext: ExecutionContext = system.dispatcher
  def usersService: ActorRef = ???
}
