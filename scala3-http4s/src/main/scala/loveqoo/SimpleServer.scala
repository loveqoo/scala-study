package loveqoo

import cats.effect.Async
import fs2.io.net.Network
import loveqoo.domain.echo.{EchoService, EchoHttpApp}
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.server.middleware.Logger

object SimpleServer:
  def run[F[_] : Async : Network](rawConfig: RawConfig): F[Nothing] = {
    val echoApp = EchoHttpApp[F](EchoService.instance[F]).routes.orNotFound
    val httpApp = Logger.httpApp(logHeaders = true, logBody = true)(echoApp)
    val config = rawConfig.server()
    for {
      _ <- EmberServerBuilder.default[F]
        .withHost(config.host)
        .withPort(config.port)
        .withHttpApp(httpApp)
        .build
    } yield ()
  }.useForever
