package loveqoo.infrastructure.database

import loveqoo.infrastructure.database.TableConfigure.*
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery

import scala.util.Using

trait TableConfigure extends JdbcConfigAware {

  import jdbcConfig.profile.api.*

  def query[T](f: jdbcConfig.profile.backend.JdbcDatabaseDef => T): Unit = {
    Using(jdbcConfig.db)(f)
  }

  class Supplier(tag: Tag) extends Table[(Int, String, String, String, String, String)](tag, Tables.Supplier) {
    def id = column[Int](Columns.Supplier.Id, O.PrimaryKey)

    def name = column[String](Columns.Supplier.Name)

    def street = column[String](Columns.Supplier.Street)

    def city = column[String](Columns.Supplier.City)

    def state = column[String](Columns.Supplier.State)

    def zip = column[String](Columns.Supplier.Zip)

    def * = (id, name, street, city, state, zip)
  }

  val suppliers = TableQuery[Supplier]

  class Coffees(tag: Tag) extends Table[(String, Int, Double, Int, Int)](tag, Tables.Coffee) {
    def name = column[String](Columns.Coffee.Name, O.PrimaryKey)

    def supID = column[Int](Columns.Coffee.SupId)

    def price = column[Double](Columns.Coffee.Price)

    def sales = column[Int](Columns.Coffee.Sales)

    def total = column[Int](Columns.Coffee.Total)

    def * = (name, supID, price, sales, total)

    def supplier = foreignKey("SUP_FK", supID, suppliers)(_.id)
  }

  def initializer(onInitialize: TableConfigure => Unit): DatabaseInitializer = new DatabaseInitializer {
    override val tableConfigure: TableConfigure = TableConfigure.this
    override val jdbcConfig: DatabaseConfig[JdbcProfile] = tableConfigure.jdbcConfig
    override val initialize: TableConfigure => Unit = onInitialize
  }

  val coffees = TableQuery[Coffees]

  object TableQueries {
    val Supplier = suppliers
    val Coffee = coffees
  }
}

object TableConfigure {

  def apply(config: DatabaseConfig[JdbcProfile]): TableConfigure = new TableConfigure {
    override val jdbcConfig: DatabaseConfig[JdbcProfile] = config
  }

  object Tables {
    val Supplier = "Supplier"
    val Coffee = "Coffee"
  }

  object Columns {
    object Supplier {
      val Id = "SUP_ID"
      val Name = "SUP_NAME"
      val Street = "STREET"
      val City = "CITY"
      val State = "STATE"
      val Zip = "ZIP"
    }

    object Coffee {
      val Name = "COF_NAME"
      val SupId = "SUP_ID"
      val Price = "PRICE"
      val Sales = "SALES"
      val Total = "TOTAL"
    }
  }
}
