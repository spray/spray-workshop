package spray.workshop

import org.specs2.mutable.Specification
import spray.testkit._
import spray.http._
import scala.xml.NodeSeq

class MyServiceSpec extends Specification with Specs2RouteTest with MyService {
  def actorRefFactory = system

  "MyService" should {
    "return a greeting for GET requests to the root path" in {
      Get() ~> myRoute ~> check {
        responseAs[NodeSeq] ===
  // format: OFF
    <html>
      <body>
        <div>Hello World</div>
        <a href="http://xkcd.com/927/"><img src="standards.png"></img></a>
      </body>
    </html>
        // format: ON
      }
    }
    "greet particular user" in {
      Get("/users/john/greet") ~> myRoute ~> check {
        responseAs[String] === "Hello john"
      }
    }
    "do calculation" in {
      Get("/add/30/58") ~> myRoute ~> check {
        responseAs[String] === "88"
      }

      Get("/add/10/1024") ~> myRoute ~> check {
        responseAs[String] === "1034"
      }
    }

  }
}
