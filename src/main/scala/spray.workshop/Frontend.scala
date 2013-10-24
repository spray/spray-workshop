package spray.workshop

import akka.actor._
import spray.routing._
import spray.routing.authentication.{ UserPass, BasicAuth }
import scala.concurrent.{ Future, ExecutionContext }
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import spray.json.DefaultJsonProtocol
import spray.http.{ StatusCodes, Uri }

class FrontendActor extends Actor with FrontendService {
  implicit def actorRefFactory = context
  implicit def executionContext: ExecutionContext = context.dispatcher
  implicit def timeout: Timeout = Timeout(2.seconds)

  val usersService: ActorRef = context.actorOf(Props(classOf[UsersService]))

  {
    val alan = (usersService ? UsersService.CreateUser("alan")).mapTo[UsersService.UserCreated]
    val ada = (usersService ? UsersService.CreateUser("ada")).mapTo[UsersService.UserCreated]

    alan.foreach { al =>
      al.userId.ref ! UserService.PostMessage(al.userId.newMessage("I'm alive"))
      al.userId.ref ! UserService.PostMessage(al.userId.newMessage("evila m'I"))
    }
  }

  def receive = runRoute(route ~ staticRoute)

  def staticRoute =
    path("index.html")(getFromResource("web/index.html")) ~ getFromResourceDirectory("web")
}

trait TestJs {
  case class Fruit(name: String)

  object FruitProtocol {
    import spray.json.DefaultJsonProtocol._
    implicit val format = jsonFormat1(Fruit)
  }

  val fruits = Fruit("apple") :: Fruit("orange") :: Fruit("banana") :: Nil
}

trait FrontendService extends HttpService with UserJsonProtocol with TestJs {
  import DefaultJsonProtocol._
  import spray.httpx.SprayJsonSupport._

  implicit def executionContext: ExecutionContext
  implicit def timeout: Timeout

  def pathEnd = rawPathPrefix(Slash.? ~ PathEnd)
  override def path[L <: shapeless.HList](pm: PathMatcher[L]): Directive[L] = rawPathPrefix((Slash?) ~ pm ~ (Slash?) ~ PathEnd)

  def usersService: ActorRef

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
        get(
          complete(
            (usersService ? UsersService.GetUsers) map {
              case UsersService.Users(users) => users
            }
          )
        )
      ) ~
      path("user" / Segment)( user =>
        get(
          complete(
            findUser(user).flatMap { user =>
              (user.ref ? UserService.GetTimeline(user)).mapTo[Timeline]
            }
          )
        )
      ) ~
        path("fruits") (
          get {
            import FruitProtocol._
            complete(fruits)
          }
        )
    ) ~
    pathPrefix("home")(
      authenticate(BasicAuth(authenticateUser _, "spray-workshop"))(user =>
        post(
          path("newMessage")(
            entity(as[String]) { msg =>
              val result = (user.ref ? UserService.PostMessage(user.newMessage(msg))).mapTo[UserService.NewMessagePosted]
              complete(result.map(_ => "Successfully posted!").recover { case _ => "Couldn't be posted" } )
            }
          )
        ) ~
          path("")(
            get(complete(s"Hello ${user.id}"))
        )
      )
    )

  // format: ON
  def resolveUserId(id: String): UserId = ???
  def apiUriFor(user: UserId): Uri = Uri("/api/user/" + user.id)
  def htmlUriFor(user: UserId): Uri = Uri("/" + user.id)

  def findUser(id: String): Future[UserId] =
    (usersService ? UsersService.FindUser(id)).mapTo[UserId]

  def authenticateUser(user: Option[UserPass]): Future[Option[UserId]] =
    user match {
      case Some(user) => findUser(user.user).map(Some(_)).recover { case _ => None }
      case None       => Future.successful(None)
    }
}

