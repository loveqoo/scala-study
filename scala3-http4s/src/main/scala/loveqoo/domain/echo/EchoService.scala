package loveqoo.domain.echo

import cats.Applicative
import cats.syntax.all.*
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe.*

trait EchoService[F[_]]:
  def handle(req: EchoService.Request): F[EchoService.Response]

object EchoService:

  val JsonKeyName = "value"

  final case class Request(value: String) extends AnyVal

  final case class Response(value: String) extends AnyVal

  private object Response:
    given Encoder[Response] = new Encoder[Response]:
      final def apply(a: Response): Json = Json.obj(
        (JsonKeyName, Json.fromString(a.value)),
      )

    given[F[_]]: EntityEncoder[F, Response] =
      jsonEncoderOf[F, Response]

  def instance[F[_] : Applicative]: EchoService[F] = new EchoService[F]:
    def handle(req: Request): F[Response] =
      Response(req.value).pure[F]
