package filesystem

import scala.annotation.tailrec

object Paths {
  val SEPARATOR: String = "/"
  val CUR_DIR: String = "."
  val PREV_DIR: String = ".."

  def isRoot(path: String): Boolean = SEPARATOR.equals(path)
  def startsFromRoot(path: String): Boolean = path.startsWith(SEPARATOR);

  def fullPath(name: String, path: String): String = {
    val separator = if (Paths.isRoot(path)) "" else Paths.SEPARATOR
    s"$path$separator$name"
  }

  def splitInSegments(path: String): List[String] =
    path.split(SEPARATOR)
      .filter(_.nonEmpty)
      .toList

  @tailrec
  def collapse(segments: List[String], accum: List[String] = List()): List[String] = {
    if (segments.isEmpty) accum
    else if (CUR_DIR.equals(segments.head)) collapse(segments.tail, accum)
    else if (PREV_DIR.equals(segments.head) && accum.nonEmpty) collapse(segments.tail, accum.init)
    else collapse(segments.tail, accum :+ segments.head)
  }
}
