package services


import javax.inject._
import models.Airport

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext


@Singleton
class Reports @Inject()(airports: Airports, countries: Countries, runways: Runways) {

  val sortedAirports = airports.data.map(_.groupBy(_.countryCode).toVector.sortBy(_._2.size))
  val top10Countries: Future[Vector[(String, Int)]] = {
    sortedAirports.flatMap(airports => Future.sequence(airports.reverse.take(10).map(toResultsSet)))
  }

  val bottom10Countries: Future[Vector[(String, Int)]] = {
    sortedAirports.flatMap(airports => Future.sequence(airports.take(10).map(toResultsSet)))
  }

  private def toResultsSet(m: (String, Seq[Airport])): Future[(String, Int)] =
    countries.fromCode(m._1).map {
      country => (country.map(_.name).getOrElse(""), m._2.size)
    }


  val typeOfRunways: Future[Vector[(String,Set[String])]] = {
    airports.data.flatMap(d =>
      Future.sequence(d.groupBy(_.countryCode).map({
        case (cc: String, airports: Seq[Airport]) => {
          for {
            typeOfRunways <- runways.data.map(_.filter(r => airports.map(_.id).contains(r.airportRef)).map(_.surface).toSet)
            countryName <- countries.fromCode(cc).map(_.map(_.name).getOrElse(""))
          } yield {
            (countryName, typeOfRunways)
          }
        }
      }).toVector
    ))
  }

  val top10RunwaysIdent: Future[Vector[(String, Int)]] = {
    runways.data.map(_.groupBy(_.leIdent).toVector.sortBy(_._2.size).reverse.take(10).map {
      case (rwIdent: String, runways: Seq[runways]) => (rwIdent, runways.size)
    })
  }
}
