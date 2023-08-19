package loveqoo.infrastructure

import cats.effect.IO
import com.typesafe.config.ConfigFactory
import loveqoo.RawConfig.getClass
import loveqoo.infrastructure.database.{DatabaseInitializer, TableConfigure}
import munit.CatsEffectSuite
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

class DatabaseSpec extends CatsEffectSuite {

  test("load slick profile value from config") {
    val profile = for {
      config <- IO(ConfigFactory.load(getClass.getClassLoader))
      profile <- IO(config.getString("slick.default.profile"))
    } yield profile
    assertIO(profile, "slick.jdbc.H2Profile$")
  }

  test("load slick profile object from config") {
    val profile = for {
      databaseConfig <- IO(DatabaseConfig.forConfig[JdbcProfile]("slick.default"))
      jdbcProfile <- IO(databaseConfig.profile)
    } yield jdbcProfile
    assertIO(profile.map(it => it.toString), "slick.jdbc.H2Profile$")
  }

  test("load TableConfigure") {
    val tableConfigure = for {
      databaseConfig <- IO(DatabaseConfig.forConfig[JdbcProfile]("slick.default"))
      tableConfigure <- IO(TableConfigure(databaseConfig))
    } yield tableConfigure
    assertIO(tableConfigure.map(it => it != null), true)
  }

  test("load DatabaseInitializer") {
    val initializer = for {
      databaseConfig <- IO(DatabaseConfig.forConfig[JdbcProfile]("slick.default"))
      tableConfigure <- IO(TableConfigure(databaseConfig))
      initializer <- IO(tableConfigure.initializer(DatabaseInitializer.h2Initializer))
    } yield initializer
    assertIO(initializer.map(it => it != null), true)
  }
}
