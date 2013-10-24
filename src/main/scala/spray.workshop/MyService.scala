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
        complete(index)
      }
    } ~ getFromResourceDirectory("web")
  // format: ON

  def index =
    <html>
      <body>
        <div>Hello World</div>
        <a href="http://xkcd.com/927/"><img src="standards.png"></img></a>
      </body>
    </html>
}
