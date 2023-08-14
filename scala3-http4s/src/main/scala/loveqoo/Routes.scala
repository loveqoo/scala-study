package loveqoo

import cats.effect.Sync
import cats.syntax.all.*
import loveqoo.service.Echo
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object Routes:
  def echoRoutes[F[_] : Sync](echo: Echo[F]): HttpRoutes[F] =
    val dsl = new Http4sDsl[F] {}
    import dsl.*
    HttpRoutes.of[F] {
      case GET -> Root / Echo.PathName =>
        echo.handle(Echo.Request("")).flatMap(Ok(_))
      case GET -> Root / Echo.PathName / msg =>
        echo.handle(Echo.Request(msg)).flatMap(Ok(_))
    }
