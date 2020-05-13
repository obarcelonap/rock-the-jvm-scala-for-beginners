package filesystem

case class File(name: String, path: String = "/", content: String = "") extends FileEntry {

  override def withPath(path: String): FileEntry = copy(path = path)
}

object File {
  def fromFullPath(filePath: String): FileEntry = {
    val filePathSegments :+ fileName = Paths.segments(filePath)
    File(fileName, Paths.asPath(filePathSegments))
  }
}
