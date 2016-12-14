package services

import javax.inject.Inject

import models.Airport

class Reports @Inject()(airports: Airports, countries: Countries, runways: Runways) {

  lazy val top10Countries: Vector[(String, Int)] = {
    toResultsSet(airports.data.groupBy(_.countryCode).toVector.sortBy(_._2.size).reverse.take(10))
  }

  lazy val bottom10Countries: Vector[(String, Int)] = {
    toResultsSet(airports.data.groupBy(_.countryCode).toVector.sortBy(_._2.size).take(10))
  }

  private def toResultsSet(m: Vector[(String, Seq[Airport])]): Vector[(String, Int)] = m.map {
    case (cc: String, airports: Seq[Airport]) => (countries.fromCode(cc).map(_.name).getOrElse(""), airports.size)
  }

  lazy val typeOfRunways: Map[String,Set[String]] = airports.data.groupBy(_.countryCode).map {
    case (cc: String, airports: Seq[Airport]) => {
      val typeOfRunways = runways.data.filter(r => airports.map(_.id).contains(r.airportRef)).map(_.surface).toSet
      (countries.fromCode(cc).map(_.name).getOrElse(""), typeOfRunways)
    }
  }

  lazy val top10RunwaysIdent: Vector[(String, Int)] = {
    runways.data.groupBy(_.leIdent).toVector.sortBy(_._2.size).reverse.take(10).map {
      case (rwIdent: String, runways: Seq[runways]) => (rwIdent, runways.size)
    }
  }
}
