package loveqoo.infrastructure

import cats.effect.Sync
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

trait CustomHttpApp[F[_]: Sync]:
  val dsl: Http4sDsl[F] = new Http4sDsl[F] {}
  def routes: HttpRoutes[F]
