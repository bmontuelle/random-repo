package services

import javax.inject._
import models.Country

import scala.concurrent.Future

import play.api.libs.concurrent.Execution.Implicits.defaultContext

@Singleton
class Countries extends DataService[Country] {
  override val sourceFile = "countries.csv"
  override def build(columns: Seq[String]) = {
    Country(
      id = columns(0).toInt,
      code = cleanCSVString(columns(1)),
      name = cleanCSVString(columns(2))
    )
  }

  def fromCode(code: String): Future[Option[Country]] = data.map(_.find(_.code == code))

  def search(q: String): Future[Option[Country]] = data.map( d =>
    d.find(_.code.equalsIgnoreCase(q))
    .orElse(d.find( _.name.equalsIgnoreCase(q)))
    .orElse(d.find(c => q.length > 3 && c.name.toLowerCase.startsWith(q.toLowerCase)))
  )

}
