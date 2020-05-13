package filesystem

trait FileEntry {
  val name: String
  val path: String
  def withPath(path: String): FileEntry

  val fullPath: String = {
    val separator = if (Paths.isRoot(path)) "" else Paths.SEPARATOR
    s"$path$separator$name"
  }
}
