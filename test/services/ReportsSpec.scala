package services

import org.specs2.concurrent.ExecutionEnv
import org.specs2.mutable.Specification
import scala.concurrent.duration._

class ReportsSpec extends Specification {
  val service = new Reports(new Airports, new Countries, new Runways)
  "Reports.top10Countries" should {
    "sort 10 countries with the most airport count" in { implicit ee: ExecutionEnv =>
      service.top10Countries must be_==(Vector(
        ("United States", 21476),
        ("Brazil", 3839),
        ("Canada", 2453),
        ("Australia", 1906),
        ("Russia", 920),
        ("France", 789),
        ("Argentina", 713),
        ("Germany", 703),
        ("Colombia", 699),
        ("Venezuela", 592)
      )).awaitFor(180 seconds)
    }
  }

  "Reports.bottom10Countries" should {
    "sort 10 countries with the least airport count" in { implicit ee: ExecutionEnv =>
      service.bottom10Countries must be_==(Vector(
        ("Jersey", 1),
        ("Gibraltar", 1),
        ("Norfolk Island", 1),
        ("Macau", 1),
        ("Unknown or unassigned country", 1),
        ("Saint Barthélemy", 1),
        ("Curaçao", 1),
        ("Andorra", 1),
        ("Saint Helena", 1),
        ("Cocos (Keeling) Islands", 1)
      )).awaitFor(180 seconds)
    }
  }

  "Reports.typeOfRunways" should {
    "return type of runways grouped per countries" in { implicit ee: ExecutionEnv =>
      service.typeOfRunways.map(_.take(4)) must be_==(Vector(
        ("Cape Verde", Set("ASP", "UNK")),
        ("Morocco", Set("ASP", "BIT", "UNK", "CON")),
        ("Angola", Set("ASP", "", "UNK")),
        ("Vietnam",Set("", "ASP", "CON", "UNK"))
      )).awaitFor(180 seconds)
    }
  }

  "Reports.top10RunwaysIdent" should {
    "return most popular runway ident" in { implicit ee: ExecutionEnv =>
      service.top10RunwaysIdent must be_==(Vector(
        ("H1", 5560),
        ("18", 3180),
        ("09", 2580),
        ("17", 2317),
        ("16", 1559),
        ("12", 1506),
        ("14", 1469),
        ("08", 1459),
        ("13", 1447),
        ("15", 1398)
      )).awaitFor(180 seconds)
    }
  }
}
