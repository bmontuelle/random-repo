package services


import javax.inject._
import models.Airport


@Singleton
class Airports extends DataService[Airport] {
  override val sourceFile = "airports.csv"
  override def build(columns: Seq[String]): Airport = {
    Airport(
      id = columns(0).toInt,
      ident = cleanCSVString(columns(1)),
      kind = cleanCSVString(columns(2)),
      name = cleanCSVString(columns(3)),
      countryCode = cleanCSVString(columns(8))
    )
  }
}
