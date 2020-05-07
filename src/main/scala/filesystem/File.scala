package filesystem

case class File(name: String, path: String = "", content: String = "") extends FileEntry {

  override def withPath(path: String): FileEntry = copy(path = path)
}
