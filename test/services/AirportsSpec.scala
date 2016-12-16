package services

import models.{Runway, Airport}
import org.specs2.concurrent.ExecutionEnv
import org.specs2.mutable.Specification
import scala.concurrent.duration._

class AirportsSpec extends Specification {
  val service = new Airports(new Runways)
  "Airports.build" should {
    "Populate Airport from columns" in {
      service.build(Vector("1", "one", "Heliport", "Heli port", "1", "3", "a", "b", "DE")) ==== Airport(1, "one", "Heliport", "Heli port", "DE")
    }
  }
  "Airports.byCountry" should {
    "Retrieve airports and runways for a given country code" in { implicit ee: ExecutionEnv =>

      val expected = Vector(
        (Airport(2901, "FMCH", "medium_airport", "Prince Said Ibrahim International Airport","KM"),
          Vector(Runway(235275, 2901,"FMCH","ASP","02"))),
        (Airport(2902, "FMCI", "medium_airport", "Mohéli Bandar Es Eslam Airport", "KM"),
          Vector(Runway(235274, 2902, "FMCI", "ASP", "13"))),
        (Airport(32737, "FMCN", "small_airport", "Iconi Airport", "KM"),
          Vector(Runway(259684, 32737, "FMCN", "UNK", "03"))),
        (Airport(2903, "FMCV", "medium_airport", "Ouani Airport", "KM"),
          Vector(Runway(235276, 2903, "FMCV", "ASP", "10"))))

      service.byCountry("KM") must be_==(expected).awaitFor(60 seconds)
    }
  }
}
