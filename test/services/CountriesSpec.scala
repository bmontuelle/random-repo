package services

import models.Country
import org.specs2.concurrent.ExecutionEnv
import org.specs2.mutable.Specification
import scala.concurrent.duration._

class CountriesSpec extends Specification {
  val service = new Countries

  "Countries.build" should {
    "Populate Country from columns" in {
      service.build(Seq("876", "FR", "France")) must be_==(Country(876, "FR", "France"))
    }
  }

  "Countries.fromCode" should {
    "retrieve existing coutries " in { implicit ee: ExecutionEnv =>
      service.fromCode("US") must be_==(Some(Country(302755, "US", "United States"))).awaitFor(60 seconds)
      service.fromCode("MP") must be_==(Some(Country(302771, "MP", "Northern Mariana Islands"))).awaitFor(60 seconds)
      service.fromCode("BG") must be_==(Some(Country(302677, "BG", "Bulgaria"))).awaitFor(60 seconds)
    }
  }

  "Countries.search" should {
    "match with country code" in { implicit ee: ExecutionEnv =>
      service.search("AU") must be_==(Some(Country(302764, "AU", "Australia"))).awaitFor(60 seconds)
      service.search("DE") must be_==(Some(Country(302681, "DE", "Germany"))).awaitFor(60 seconds)
    }

    "match with complete country name" in { implicit ee: ExecutionEnv =>
      service.search("united states") must be_==(Some(Country(302755, "US", "United States"))).awaitFor(60 seconds)
      service.search("Saint Vincent and the Grenadines") must be_==(Some(Country(302756, "VC", "Saint Vincent and the Grenadines"))).awaitFor(60 seconds)
    }

    "match with partial country name" in { implicit ee: ExecutionEnv =>
      service.search("turkmen") must be_==(Some(Country(302666, "TM", "Turkmenistan"))).awaitFor(60 seconds)
      service.search("Saint pierre") must be_==(Some(Country(302750, "PM", "Saint Pierre and Miquelon"))).awaitFor(60 seconds)
    }

    "may not match anything" in { implicit ee: ExecutionEnv =>
      service.search("azkdlmazkmlzakdmlk") must be_==(None).awaitFor(60 seconds)
      service.search("xx") must be_==(None).awaitFor(60 seconds)
    }
  }
}
