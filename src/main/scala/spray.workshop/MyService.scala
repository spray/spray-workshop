package spray.workshop

import akka.actor._
import spray.routing._
import spray.http.StatusCodes
import spray.routing.authentication.BasicAuth
import spray.workshop.UsernameEqualsPasswordAuthenticator
import scala.concurrent.ExecutionContext

class MyService extends Actor with Service {
  implicit def actorRefFactory = context
  implicit def executionContext: ExecutionContext = context.dispatcher

  def receive = runRoute(route ~ staticRoute)
}

trait Service extends HttpService {
  implicit def executionContext: ExecutionContext

  case class Fruit(name: String)

  val fruits = Fruit("apple") :: Fruit("orange") :: Fruit("banana") :: Nil

  // format: OFF
  def route =
    // public
    path("")(
      get (
        complete("Hello world!")
      )
    ) ~
    pathPrefix("home")(
      authenticate(BasicAuth(UsernameEqualsPasswordAuthenticator, "spray-workshop"))(user =>
        path("")(
          complete(s"Hello ${user.username}")
        )
      )
    ) ~
    path("api" / "fruits") (
      get (
        complete("")
      )
    )

  def staticRoute =
    path("index.html")(getFromResource("web/index.html")) ~ getFromResourceDirectory("web")

  // format: ON
}
