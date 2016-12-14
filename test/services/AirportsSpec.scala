package services

import models.Airport
import org.specs2.mutable.Specification

class AirportsSpec extends Specification {
  "Airports.build" should {
    "Populate Airport from columns" in {
      val service = new Airports

      service.build(Seq("1", "one", "Heliport", "Heli port", "1", "3", "a", "b", "DE")) ==== Airport(1, "one", "Heliport", "Heli port", "DE")
    }
  }
}
