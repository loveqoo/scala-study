package loveqoo

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple:
  val run: IO[Unit] = {
    RawConfig().flatMap(SimpleServer.run[IO](_)).onError(e => IO(println(e.getMessage)))
  }
