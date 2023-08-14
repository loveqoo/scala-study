package loveqoo

import cats.effect.IO
import loveqoo.service.Echo
import munit.CatsEffectSuite
import org.http4s.{Method, Request, Response, Status, Uri}

import scala.util.Random

class EchoSpec extends CatsEffectSuite:

  private[this] def run(message: String = "hello"): IO[Response[IO]] = {
    val request = Request[IO](Method.GET, Uri.unsafeFromString(s"/${Echo.PathName}/$message"))
    Routes.echoRoutes(Echo.instance[IO]).orNotFound.run(request)
  }

  test("echo returns status code 200") {
    assertIO(run().map(_.status), Status.Ok)
  }

  test("echo returns message") {
    val message = Random.alphanumeric.take(10).mkString
    assertIO(run(message).flatMap(_.as[String]), s"{\"${Echo.JsonKeyName}\":\"$message\"}")
  }
