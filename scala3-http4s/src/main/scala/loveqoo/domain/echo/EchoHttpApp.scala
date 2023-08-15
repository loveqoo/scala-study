package loveqoo.domain.echo

import cats.effect.Sync
import cats.syntax.all.*
import loveqoo.domain.echo.EchoHttpApp.PathName
import loveqoo.infrastructure.CustomHttpApp
import org.http4s.HttpRoutes

class EchoHttpApp[F[_]: Sync](echo: EchoService[F]) extends CustomHttpApp[F]:
  def routes: HttpRoutes[F] =
    import dsl.*
    HttpRoutes.of[F] {
      case GET -> Root / PathName =>
        echo.handle(EchoService.Request("")).flatMap(Ok(_))
      case GET -> Root / PathName / msg =>
        echo.handle(EchoService.Request(msg)).flatMap(Ok(_))
    }
object EchoHttpApp {
  val PathName: String = "echo"
  def apply[F[_] : Sync](echo: EchoService[F]): EchoHttpApp[F] = new EchoHttpApp(echo)
}
