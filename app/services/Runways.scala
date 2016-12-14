package services

import models.{Runway, Airport}


class Runways extends DataService[Runway] {
  override val sourceFile = "runways.csv"
  override def build(columns: Seq[String]) = {
    Runway(
      id = columns(0).toInt,
      airportRef = columns(1).toInt,
      airportIdent = columns(2),
      leIdent = columns(8)
    )
  }
}
