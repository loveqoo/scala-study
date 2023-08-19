package loveqoo.infrastructure.database

trait DatabaseInitializer extends TableConfigureAware {
  val initialize: TableConfigure => Unit
  def run = initialize(tableConfigure)
}

object DatabaseInitializer {

  val h2Initializer: TableConfigure => Unit = {
    tableConfigure =>
      import tableConfigure.TableQueries.*
      import tableConfigure.jdbcConfig.profile.api.*

      tableConfigure.query { db =>
        db.run(DBIO.seq(
          Supplier.schema.create,
          Coffee.schema.create,
          Supplier ++= Seq(
            (101, "Acme, Inc.", "99 Market Street", "Groundsville", "CA", "95199"),
            (49, "Superior Coffee", "1 Party Place", "Mendocino", "CA", "95460"),
            (150, "The High Ground", "100 Coffee Lane", "Meadows", "CA", "93966")
          ),
          Coffee ++= Seq(
            ("Colombian", 101, 7.99, 0, 0),
            ("French_Roast", 49, 8.99, 0, 0),
            ("Espresso", 150, 9.99, 0, 0),
            ("Colombian_Decaf", 101, 8.99, 0, 0),
            ("French_Roast_Decaf", 49, 9.99, 0, 0)
          )
        ))
      }
  }
}
