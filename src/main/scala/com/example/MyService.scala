package com.example

import akka.actor._
import spray.routing._

class MyService extends Actor with HttpService {
  implicit def actorRefFactory = context

  def receive = runRoute(myRoute)

  val myRoute =
    get {
      complete("Hello World")
    }
}
