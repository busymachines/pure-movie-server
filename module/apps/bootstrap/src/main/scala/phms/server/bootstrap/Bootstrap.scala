package phms.server.bootstrap

import phms._
import phms.algebra.user.{UserAccountAlgebra, UserAccountBootstrapAlgebra}
import phms.kernel._

/** @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 13 Jul 2018
  */
object Bootstrap {

  object superAdmin {
    def email[F[_]: MonadThrow]: F[Email]             = Email[F]("murray.bookchin@socialecology.fecund")
    def passw[F[_]: MonadThrow]: F[PlainTextPassword] = PlainTextPassword[F]("OldManYellsAtAnarchism")

    //the concatenated base64($email:$passw) string, on hand, in case needed
    val BasicAuthEncoding: String =
      "bXVycmF5LmJvb2tjaGluQHNvY2lhbGVjb2xvZ3kuZmVjdW5kOk9sZE1hblllbGxzQXRBbmFyY2hpc20="
  }

  def bootstrap[F[_]: Sync: Console](
    uca: UserAccountAlgebra[F],
    uba: UserAccountBootstrapAlgebra[F],
  ): F[Unit] =
    for {
      usb   <- ServerBootstrapAlgebra.create[F](uca, uba).pure[F]
      email <- superAdmin.email[F]
      passw <- superAdmin.passw[F]
      _     <- Console[F].println("Bootstrapping user")
      _     <- usb.bootStrapSuperAdmin(email, passw).attempt.void
    } yield ()

}