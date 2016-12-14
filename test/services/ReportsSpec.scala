package services

import org.specs2.mutable.Specification

class ReportsSpec extends Specification {
  val service = new Reports(new Airports, new Countries, new Runways)
  "Reports.top10Countries" should {
    "sort 10 countries with the most airport count" in {
      service.top10Countries ==== Vector(
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
      )
    }
  }

  "Reports.bottom10Countries" should {
    "sort 10 countries with the least airport count" in {
      service.bottom10Countries ==== Vector(
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
      )
    }
  }


  "Reports.typeOfRunways" should {
    service.typeOfRunways.take(4) ==== Map(
      "Gibraltar" -> Set("ASP"),
      "Haiti" -> Set("ASP"),
      "British Virgin Islands" -> Set("UNK", "ASP"),
      "" -> Set("Gravel", "UNK", "Gravel/Grass", "ASP", "Grass")
    )
  }
}
