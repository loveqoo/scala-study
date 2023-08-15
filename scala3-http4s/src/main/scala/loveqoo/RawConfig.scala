package loveqoo

import cats.effect.IO
import com.comcast.ip4s.{IpAddress, Port, ipv4, port}
import com.typesafe.config.ConfigFactory

case class RawConfig(host: String, port: Int) {
  def server(): ServerConfig = ServerConfig(
    IpAddress.fromString(host).getOrElse(ipv4"0.0.0.0"),
    Port.fromInt(port).getOrElse(port"8080")
  )
}

case class ServerConfig(host: IpAddress, port: Port)

object RawConfig {
  def apply(): IO[RawConfig] = {
    for {
      config <- IO(ConfigFactory.load(getClass.getClassLoader))
      host <- IO(config.getString("server.host"))
      port <- IO(config.getInt("server.port"))
    } yield RawConfig(host, port)
  }
}
