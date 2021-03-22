package phms.algebra.user

import phms.algebra.user.impl.UserAlgebraImpl
import phms._
import phms.kernel._
import phms.db._

/** @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 20 Jun 2018
  */
trait UserAccountAlgebra[F[_]] {

  implicit protected def monadThrow: MonadError[F, Throwable]
  protected def authAlgebra:         UserAuthAlgebra[F]

  final def invitationStep1(
    inv:  UserInvitation
  )(implicit
    auth: AuthCtx
  ): F[UserInviteToken] =
    authAlgebra.authorizeGTERoleThan(inv.role)(registrationStep1Impl(inv))

  protected[user] def registrationStep1Impl(inv: UserInvitation): F[UserInviteToken]

  def invitationStep2(token: UserInviteToken, pw: PlainTextPassword): F[User]

  def resetPasswordStep1(email: Email): F[PasswordResetToken]

  def resetPasswordStep2(token: PasswordResetToken, newPassword: PlainTextPassword): F[Unit]
}

object UserAccountAlgebra {

  def resource[F[_]](implicit dbPool: DDPool[F], F: Async[F], sr: SecureRandom[F]): Resource[F, UserAccountAlgebra[F]] =
    Resource.pure[F, UserAccountAlgebra[F]](new UserAlgebraImpl[F]())
}