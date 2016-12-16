package services


import javax.inject._

import models.{Airport, Runway}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future


@Singleton
class Airports @Inject()(runways: Runways) extends DataService[Airport] {
  override val sourceFile = "airports.csv"
  override def build(columns: Vector[String]): Airport = {
    Airport(
      id = columns(0).toInt,
      ident = cleanCSVString(columns(1)),
      kind = cleanCSVString(columns(2)),
      name = cleanCSVString(columns(3)),
      countryCode = cleanCSVString(columns(8))
    )
  }

  def byCountry(countryCode: String): Future[Vector[(Airport, Vector[Runway])]] = {
    for {
      airports <- data.map(_.filter(_.countryCode == countryCode))
      runways <- runways.data.map(_.filter(rw => airports.map(_.ident).contains(rw.airportIdent)).groupBy(_.airportIdent))
    } yield {
      airports.map(airport => {
        (airport, runways.get(airport.ident).getOrElse(Vector()))
      })
    }
  }
}
