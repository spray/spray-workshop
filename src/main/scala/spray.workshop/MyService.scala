package com.example

import akka.actor._
import spray.routing._
import spray.http.StatusCodes
import spray.routing.authentication.BasicAuth
import spray.workshop.UsernameEqualsPasswordAuthenticator

class MyService extends Actor with HttpService {
  implicit def actorRefFactory = context
  import context.dispatcher

  def receive = runRoute(myRoute)

  // format: OFF
  def myRoute =
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

  // format: ON
}
