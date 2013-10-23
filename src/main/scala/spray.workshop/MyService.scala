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
    )

  def staticRoute =
    getFromResourceDirectory("web")

  // format: ON
}
