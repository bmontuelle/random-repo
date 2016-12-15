package services

import org.specs2.concurrent.ExecutionEnv
import org.specs2.mutable.Specification
import scala.concurrent.duration._

class DataServiceSpec extends Specification {
  "DataService" should {
    "read a csv file lines" in { implicit ee: ExecutionEnv =>
      object dataServiceTest extends DataService[Map[String, Any]] {
        override def sourceFile = "test-data.csv"
        override def build(columns: Seq[String]) = {
           Map(
             "id" -> columns(0).toInt,
             "name" -> cleanCSVString(columns(1)),
             "value" -> columns(2).toDouble
           )
        }
      }

      dataServiceTest.data must be_==(Seq(
        Map(
          "id" -> 987,
          "name" -> "John",
          "value" -> 45.09
        ),
        Map(
          "id" -> 123,
          "name" -> "Steeve",
          "value" -> 72.89
        ),
        Map(
          "id" -> 456,
          "name" -> "Mary",
          "value" -> 92.41
        ),
        Map(
          "id" -> 789,
          "name" -> "Kevin",
          "value" -> 65.9
        )
      )).awaitFor(60 seconds)
    }
  }
}
