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

  "Countries.fromCode" should {
    "retrieve existing coutries " in {
      val service = new Countries
      service.fromCode("US") ==== Some(Country(302755, "US", "United States"))

      service.fromCode("MP") ==== Some(Country(302771, "MP", "Northern Mariana Islands"))

      service.fromCode("BG") ==== Some(Country(302677, "BG", "Bulgaria"))
    }
  }
}
