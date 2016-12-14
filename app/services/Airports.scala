package services

import models.Airport


class Airports extends DataService[Airport] {
  override val sourceFile = "airports.csv"
  override def build(columns: Seq[String]): Airport = {
    Airport(
      id = columns(0).toInt,
      ident = columns(1),
      kind = columns(2),
      name = columns(3)
    )
  }
}
