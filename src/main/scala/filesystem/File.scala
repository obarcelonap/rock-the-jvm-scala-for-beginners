package filesystem

case class File(name: String, path: String = "/", content: String = "") extends FileEntry {}

object File {
  def fromFullPath(filePath: String, content: String = ""): File = {
    val filePathSegments :+ fileName = Paths.segments(filePath)
    File(fileName, Paths.asPath(filePathSegments), content)
  }
}
