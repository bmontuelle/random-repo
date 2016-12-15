package services

import scala.concurrent.Future

import play.api.libs.concurrent.Execution.Implicits.defaultContext


trait DataService[T] {
  val CSV_SEP = ','

  def sourceFile: String

  lazy val data: Future[Seq[T]] = {
    val is = this.getClass.getClassLoader.getResourceAsStream(sourceFile)
    Future(scala.io.Source.fromInputStream(is).getLines.drop(1).map(line => {
      build(line.split(CSV_SEP).take(9))
    }).foldLeft(Seq[T]()) {
      (acc: Seq[T], current: T) => { acc :+ current }
    })
  }

  def build(columns: Seq[String]): T

  protected def cleanCSVString(s: String) = s.replaceAll("""^"(.*)"$""", "$1")
}
