package services

import models.Runway

import scala.util.Try


class Runways extends DataService[Runway] {
  override val sourceFile = "runways.csv"
  override def build(columns: Seq[String]) = {
    Runway(
      id = columns(0).toInt,
      airportRef = columns(1).toInt,
      airportIdent = columns(2),
      surface = columns(5),
      leIdent = Try(columns(8)).getOrElse("")
    )
  }
}
