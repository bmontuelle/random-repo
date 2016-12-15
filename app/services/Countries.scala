package services

import models.{Country, Airport}


class Countries extends DataService[Country] {
  override val sourceFile = "countries.csv"
  override def build(columns: Seq[String]) = {
    Country(
      id = columns(0).toInt,
      code = columns(1),
      name = columns(2)
    )
  }

  def fromCode(code: String): Option[Country] = data.find(_.code == code)

  def search(q: String): Option[Country] = data.find(_.code.equalsIgnoreCase(q))
    .orElse(data.find( _.name.equalsIgnoreCase(q)))
    .orElse(data.find(c => q.length > 3 && c.name.toLowerCase.startsWith(q.toLowerCase)))

}
