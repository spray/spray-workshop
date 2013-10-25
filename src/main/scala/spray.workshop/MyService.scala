package spray.workshop

import akka.actor._
import spray.routing._
import spray.http.StatusCodes

class MyServiceActor extends Actor with MyService {
  implicit def actorRefFactory = context

  def receive = runRoute(myRoute)
}

trait MyService extends HttpService {
  case class CalculatedSum(a: Int, b: Int, sum: Int)
  object CalculatedSum {
    import spray.json.DefaultJsonProtocol._
    implicit val sumJsonFormat = jsonFormat3(CalculatedSum.apply _)
  }
  import spray.httpx.SprayJsonSupport._

  // format: OFF
  def myRoute =
    get(
      path("")(
        complete(index)
      ) ~
      pathPrefix("users")(
        pathPrefix(Segment)( user =>
          path("greet")(
            complete(s"Hello $user")
          ) ~
          path("saygoodbye")(
            complete(helloX(user))
          )
        )
      ) ~
      path("add" / IntNumber / IntNumber)( (a, b) =>
        complete((a + b).toString)
      )
    )~
    post(
      path("add") (
        formFields('a.as[Int], 'b.as[Int])((a, b) =>
          complete(CalculatedSum(a, b, a + b))
        )
      )
    ) ~ getFromResourceDirectory("web")
  // format: ON

  def index =
    <html>
      <body>
        <div>Hello World</div>
        <a href="http://xkcd.com/927/"><img src="standards.png"></img></a>
        <h2>Calculator</h2>
        <form action="/add" method="post">
          <div>a: <input type="text" name="a"></input></div>
          <div>b: <input type="text" name="b"></input></div>
          <div><input type="submit" value="add"></input></div>
        </form>
      </body>
    </html>

  def helloX(user: String) =
    <html>
      <body>
        <div>Goodbye { user }</div>
      </body>
    </html>
}
