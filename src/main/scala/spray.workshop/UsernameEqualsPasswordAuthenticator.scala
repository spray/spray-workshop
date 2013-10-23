/*
 * Copyright 2013 Heiko Seeberger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package spray.workshop

import scala.concurrent.{ Future, Promise }
import spray.routing.authentication.{ BasicUserContext, UserPass, UserPassAuthenticator }

object UsernameEqualsPasswordAuthenticator extends UserPassAuthenticator[BasicUserContext] {

  override def apply(userPass: Option[UserPass]): Future[Option[BasicUserContext]] = {
    val basicUserContext =
      userPass flatMap {
        case UserPass(username, password) if username == password => Some(BasicUserContext(username))
        case _ => None
      }
    Promise.successful(basicUserContext).future
  }
}
