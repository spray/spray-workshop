package spray.workshop

import org.specs2.mutable.Specification
import spray.testkit._
import spray.http._
import scala.xml.NodeSeq

class MyServiceSpec extends Specification with Specs2RouteTest with MyService {
  def actorRefFactory = system

  "MyService" should {
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
      Post("/add").withEntity(HttpEntity(MediaTypes.`application/x-www-form-urlencoded`, "a=12&b=11")) ~> myRoute ~> check {
        responseAs[String] === "23"
      }
    }

  }
}
