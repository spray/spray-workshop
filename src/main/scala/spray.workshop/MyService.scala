package com.example

import akka.actor._
import spray.routing._
import spray.http.StatusCodes

class MyService extends Actor with HttpService {
  implicit def actorRefFactory = context

  def receive = runRoute(myRoute)

  // format: OFF
  def myRoute =
    path("") {
      get {
        complete(page)
      }
    }
  // format: ON

  val page =
    <html>
      <head>
      </head>
      <body>
        Test
      </body>
    </html>
}
