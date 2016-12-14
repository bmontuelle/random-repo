package services


trait DataService[T] {
  val CSV_SEP = ','

  def sourceFile: String

  private lazy val is = this.getClass.getClassLoader.getResourceAsStream(sourceFile)

  lazy val data: Seq[T] = scala.io.Source.fromInputStream(is).getLines.drop(1).map(line => {
    build(line.split(CSV_SEP).map(_.replaceAll("""^"(.*)"$""", "$1")))
  }).foldLeft(Seq[T]()) {
    (acc: Seq[T], current: T) => { acc :+ current }
  }

  def build(columns: Seq[String]): T
}
