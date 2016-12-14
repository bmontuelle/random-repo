package services

import models.{Country, Airport}


class Countries extends DataService[Country] {
  override val sourceFile = "countries.csv"
  override def build(columns: Seq[String]) = {
    Country(
      id = columns(0).toInt,
      code = columns(1),
      name = columns(2)
    )
  }
}
