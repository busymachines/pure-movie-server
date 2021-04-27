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

package phms.stack.http

import org.http4s.*
import org.http4s.dsl.*
import org.http4s.server.AuthMiddleware
import org.typelevel.ci.CIString
import phms.algebra.user.*
import phms.*

/** @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 26 Jun 2018
  */
object AuthedHttp4s {

  def userTokenAuthMiddleware[F[_]](
    authAlgebra: UserAuthAlgebra[F]
  )(using F:  MonadThrow[F]): Resource[F, AuthMiddleware[F, AuthCtx]] = {
    val tokenVerification: Kleisli[F, Request[F], Attempt[AuthCtx]] = verifyToken[F](authAlgebra)
    AuthMiddleware(tokenVerification, onFailure[F]).pure[Resource[F, *]]
  }

  private val `X-Auth-Token` = CIString("X-AUTH-TOKEN")

  private val challenges: NonEmptyList[Challenge] = NonEmptyList.of(
    Challenge(
      scheme = "Basic",
      realm  = "Go to POST /phms/api/user/login to get valid token",
    )
  )

  private val wwwHeader = headers.`WWW-Authenticate`(challenges)

  private def onFailure[F[_]](using F: MonadThrow[F]): AuthedRoutes[Throwable, F] =
    Kleisli[OptionT[F, *], AuthedRequest[F, Throwable], Response[F]] { (_: AuthedRequest[F, Throwable]) =>
      val fdsl = Http4sDsl[F]
      import fdsl.*
      OptionT.liftF[F, Response[F]](Unauthorized(wwwHeader))
    }

  private def verifyToken[F[_]](
    authAlgebra: UserAuthAlgebra[F]
  )(using F:  MonadThrow[F]): Kleisli[F, Request[F], Attempt[AuthCtx]] =
    Kleisli { (req: Request[F]) =>
      val optHeader = req.headers.get(`X-Auth-Token`)
      optHeader match {
        case None =>
          Fail.unauthorized(s"No ${`X-Auth-Token`} provided").raiseError[Attempt, AuthCtx].pure[F]
        case Some(headers: NEList[Header.Raw]) =>
          //TODO: ensure there is only one such header
          if (headers.size != 1)
            Fail
              .unauthorized(s"Found multiple ${`X-Auth-Token`} headers. Please provide only one.")
              .raiseError[Attempt, AuthCtx]
              .pure[F]
          else {
            authAlgebra
              .authenticate(AuthenticationToken(headers.head.value))
              .map(_.pure[Attempt])
              .handleError(_.raiseError[Attempt, AuthCtx])
          }

      }
    }
}
