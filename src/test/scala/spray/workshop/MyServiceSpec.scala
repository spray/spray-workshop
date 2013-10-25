package spray.workshop

import org.specs2.mutable.Specification
import spray.testkit._
import spray.http._

class MyServiceSpec extends Specification with Specs2RouteTest with MyService {
  def actorRefFactory = system

  "MyService" should {
    "return a greeting for GET requests to the root path" in {
      Get() ~> myRoute ~> check {
        responseAs[String] must contain("Hello world!")
      }
    }
  }
}
