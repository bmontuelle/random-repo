package services

import models.Runway
import org.specs2.mutable.Specification

class RunwaysSpec extends Specification {
  "Runways.build" should {
    "Populate Runway from columns" in {
      val service = new Runways

      service.build(Seq("1", "701", "Airport_1", "z", "y", "x", "w", "v", "RW1")) ==== Runway(1, 701, "Airport_1", "RW1")
    }
  }
}
