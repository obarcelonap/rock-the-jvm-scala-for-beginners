package filesystem

import scala.annotation.tailrec

object Paths {
  val SEPARATOR: String = "/"
  val CUR_DIR: String = "."
  val PREV_DIR: String = ".."

  def isRoot(path: String): Boolean = SEPARATOR.equals(path)
  def isAbsolute(path: String): Boolean = path.startsWith(SEPARATOR)

  def concat(paths: String*): String = asPath(paths.flatMap(segments))
  def segments(path: String): List[String] =
    path.split(SEPARATOR)
      .filter(_.nonEmpty)
      .toList
  def asPath(segments: Seq[String]): String = segments.mkString(SEPARATOR, SEPARATOR, "")

  @tailrec
  def collapse(segments: List[String], accum: List[String] = List()): List[String] = {
    if (segments.isEmpty) accum
    else if (CUR_DIR.equals(segments.head)) collapse(segments.tail, accum)
    else if (PREV_DIR.equals(segments.head) && accum.nonEmpty) collapse(segments.tail, accum.init)
    else collapse(segments.tail, accum :+ segments.head)
  }
  def collapse(path: String): String = concat(collapse(segments(path)):_*)

  def splitAndCollapse(path:String): List[String] = collapse(segments(path))

  object absolutePath {
    def unapply(path: String): Option[String] = Some(path).filter(_.startsWith("/"))
  }
  object relativePath {
    def unapply(path: String): Option[String] = Some(path).filter(!_.startsWith("/"))
  }
}
