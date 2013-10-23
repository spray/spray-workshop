package spray.workshop

import akka.actor._
import spray.routing._
import spray.routing.authentication.BasicAuth
import scala.concurrent.ExecutionContext
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import spray.json.DefaultJsonProtocol

class FrontendActor extends Actor with FrontendService {
  implicit def actorRefFactory = context
  implicit def executionContext: ExecutionContext = context.dispatcher

  val usersService: ActorRef = context.actorOf(Props(classOf[UsersService]))
  usersService ! UsersService.CreateUser("alan")
  usersService ! UsersService.CreateUser("ada")

  def receive = runRoute(route)
}

trait FrontendService extends HttpService with UserJsonProtocol {
  import DefaultJsonProtocol._
  import spray.httpx.SprayJsonSupport._

  implicit def executionContext: ExecutionContext

  def usersService: ActorRef

  implicit val timeout = Timeout(2.seconds)

  // format: OFF
  def route =
    // public
    path("")(
      get (
        complete("Hello world!")
      )
    ) ~
    pathPrefix("api")(
      path("users")(
        complete {
          (usersService ? UsersService.GetUsers) map {
            case UsersService.Users(users) => users
          }
        }
      )
    ) ~
    pathPrefix("home")(
      authenticate(BasicAuth(UsernameEqualsPasswordAuthenticator, "spray-workshop"))(user =>
        path("")(
          complete(s"Hello ${user.username}")
        )
      )
    )
  // format: ON
  def resolveUserId(id: String): UserId = ???
}
