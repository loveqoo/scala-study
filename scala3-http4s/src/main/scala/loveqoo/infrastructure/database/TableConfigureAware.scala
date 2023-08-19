package loveqoo.infrastructure.database

trait TableConfigureAware extends JdbcConfigAware {
  val tableConfigure: TableConfigure
}
