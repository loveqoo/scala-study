package loveqoo

import cats.effect.Async
import com.comcast.ip4s.*
import fs2.io.net.Network
import loveqoo.service.Echo
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.server.middleware.Logger

object SimpleServer:
  def run[F[_] : Async : Network]: F[Nothing] = {
    val httpApp = Routes.echoRoutes[F](Echo.instance[F]).orNotFound
    val finalHttpApp = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)
    for {
      _ <- EmberServerBuilder.default[F]
        .withHost(ipv4"0.0.0.0")
        .withPort(port"8080")
        .withHttpApp(finalHttpApp)
        .build
    } yield ()
  }.useForever
