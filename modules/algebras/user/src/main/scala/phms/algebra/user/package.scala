package phms.algebra

import phms._
import phms.time._

/** @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 20 Jun 2018
  */
package object user {
  type UserID = UserID.Type
  object UserID extends SproutUUID

  type UserInviteToken = UserInviteToken.Type
  object UserInviteToken extends Sprout[String]

  type PasswordResetToken = PasswordResetToken.Type
  object PasswordResetToken extends Sprout[String]

  type AuthenticationToken = AuthenticationToken.Type
  object AuthenticationToken extends Sprout[String]

  type UserAuthExpiration = UserAuthExpiration.Type
  object UserAuthExpiration extends SproutTimestamp

  type UserInviteExpiration = UserInviteExpiration.Type
  object UserInviteExpiration extends SproutTimestamp

  type UserModuleAlgebra[F[_]] = UserAuthAlgebra[F] with UserAlgebra[F] with UserAccountAlgebra[F]
}