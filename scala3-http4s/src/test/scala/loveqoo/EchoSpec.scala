package loveqoo

import cats.effect.IO
import loveqoo.domain.echo.{EchoHttpApp, EchoService}
import munit.CatsEffectSuite
import org.http4s.{Method, Request, Response, Status, Uri}

import scala.util.Random

class EchoSpec extends CatsEffectSuite:

  private[this] def run(message: String = "hello"): IO[Response[IO]] = {
    val request = Request[IO](Method.GET, Uri.unsafeFromString(s"/${EchoHttpApp.PathName}/$message"))
    EchoHttpApp[IO](EchoService.instance[IO]).routes.orNotFound.run(request)
  }

  test("echo returns status code 200") {
    assertIO(run().map(_.status), Status.Ok)
  }

  test("echo returns message") {
    val message = Random.alphanumeric.take(10).mkString
    assertIO(run(message).flatMap(_.as[String]), s"{\"${EchoService.JsonKeyName}\":\"$message\"}")
  }
