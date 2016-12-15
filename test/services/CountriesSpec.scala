package services

import models.Country
import org.specs2.mutable.Specification

class CountriesSpec extends Specification {
  val service = new Countries

  "Countries.build" should {
    "Populate Country from columns" in {
      service.build(Seq("876", "FR", "France")) ==== Country(876, "FR", "France")
    }
  }

  "Countries.fromCode" should {
    "retrieve existing coutries " in {
      service.fromCode("US") ==== Some(Country(302755, "US", "United States"))
      service.fromCode("MP") ==== Some(Country(302771, "MP", "Northern Mariana Islands"))
      service.fromCode("BG") ==== Some(Country(302677, "BG", "Bulgaria"))
    }
  }

  "Countries.search" should {
    "match with country code" in {
      service.search("AU") ==== Some(Country(302764, "AU", "Australia"))
      service.search("DE") ==== Some(Country(302681, "DE", "Germany"))
    }

    "match with complete country name" in {
      service.search("united states") ==== Some(Country(302755, "US", "United States"))
      service.search("Saint Vincent and the Grenadines") ==== Some(Country(302756, "VC", "Saint Vincent and the Grenadines"))
    }

    "match with partial country name" in {
      service.search("turkmen") ==== Some(Country(302666, "TM", "Turkmenistan"))
      service.search("Saint pierre") ==== Some(Country(302750, "PM", "Saint Pierre and Miquelon"))
    }

    "may not match anything" in {
      service.search("azkdlmazkmlzakdmlk") ==== None
      service.search("xx") ==== None
    }
  }
}
