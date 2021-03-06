/*
 * Copyright 2021 BusyMachines
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package phms.api.user

import org.http4s.dsl._
import phms.stack.http._
import phms.algebra.user._
import phms._

/** @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 26 Jun 2018
  */
final class UserRoutes[F[_]](
  private val userAlgebra: UserAlgebra[F]
)(implicit
  val F:                   Concurrent[F],
  val D:                   Defer[F],
) extends Http4sDsl[F] with UserRoutesJSON {

  private val userRestRoutes: AuthCtxRoutes[F] = AuthCtxRoutes[F] {
    case GET -> Root / "user" / UUIDVar(userID) as user =>
      for {
        resp <- Ok(userAlgebra.findUser(UserID(userID))(user))
      } yield resp
  }

  val authedRoutes: AuthCtxRoutes[F] = userRestRoutes

}
