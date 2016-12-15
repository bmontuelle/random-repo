package services

import javax.inject._
import models.Runway
import scala.util.Try


@Singleton
class Runways extends DataService[Runway] {
  override val sourceFile = "runways.csv"
  override def build(columns: Seq[String]) = {
    Runway(
      id = columns(0).toInt,
      airportRef = columns(1).toInt,
      airportIdent = cleanCSVString(columns(2)),
      surface = cleanCSVString(columns(5)),
      leIdent = Try(cleanCSVString(columns(8))).getOrElse("")
    )
  }
}
