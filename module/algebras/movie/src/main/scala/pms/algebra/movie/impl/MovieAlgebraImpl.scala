package pms.algebra.movie.impl

import pms.db._
import pms.algebra.movie._
import pms.algebra.user.UserAuthAlgebra
import pms.core._

/**
  *
  * @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 25 Jun 2018
  *
  */
final private[movie] class MovieAlgebraImpl[F[_]: BracketThrow](
  override protected val userAuth: UserAuthAlgebra[F],
  private val transactor:          Transactor[F],
) extends MovieAlgebra[F] {

  override protected def createMovieImpl(mc: MovieCreation): F[Movie] =
    MovieAlgebraSQL.insertMovie(mc).transact(transactor)

  override protected def findMoviesBetweenImpl(interval: QueryInterval): F[List[Movie]] =
    MovieAlgebraSQL.findBetween(interval).transact(transactor)

  override protected def findMovieImpl(mid: MovieID): F[Movie] =
    MovieAlgebraSQL.fetchByIDQuery(mid).transact(transactor)
}