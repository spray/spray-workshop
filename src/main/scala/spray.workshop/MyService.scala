package spray.workshop

import akka.actor._
import spray.routing._
import spray.http.StatusCodes

class MyServiceActor extends Actor with MyService {
  implicit def actorRefFactory = context

  def receive = runRoute(myRoute)
}

trait MyService extends HttpService {
  // format: OFF
  def myRoute =
    get {
      path("") {
        complete("Hello world!")
      }
    }
  // format: ON
}
