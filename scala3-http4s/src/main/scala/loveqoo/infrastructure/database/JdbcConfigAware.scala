package loveqoo.infrastructure.database

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

trait JdbcConfigAware {
  val jdbcConfig: DatabaseConfig[JdbcProfile]
}
