package loveqoo

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple:
  val run: IO[Unit] = SimpleServer.run[IO]
