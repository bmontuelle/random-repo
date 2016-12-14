package services

import models.Country
import org.specs2.mutable.Specification

class CountriesSpec extends Specification {
  "Countries.build" should {
    "Populate Country from columns" in {
      val service = new Countries

      service.build(Seq("876", "FR", "France")) ==== Country(876, "FR", "France")
    }
  }
}
