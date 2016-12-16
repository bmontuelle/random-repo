package services

import scala.concurrent.Future

import play.api.libs.concurrent.Execution.Implicits.defaultContext


trait DataService[T] {
  val CSV_SEP = ','

  def sourceFile: String

  lazy val data: Future[Vector[T]] = {
    val is = this.getClass.getClassLoader.getResourceAsStream(sourceFile)
    Future(scala.io.Source.fromInputStream(is).getLines.drop(1).map(line => {
      build(line.split(CSV_SEP).take(9).toVector)
    }).foldLeft(Vector[T]()) {
      (acc: Vector[T], current: T) => { acc :+ current }
    })
  }

  def build(columns: Vector[String]): T

  protected def cleanCSVString(s: String) = s.replaceAll("""^"(.*)"$""", "$1")
}
