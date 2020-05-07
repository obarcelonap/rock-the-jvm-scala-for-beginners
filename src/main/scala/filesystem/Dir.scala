package filesystem

import scala.annotation.tailrec

case class Dir(name: String, path: String = "", entries: List[FileEntry] = List())
  extends FileEntry {

  val fullPath: String = Paths.fullPath(name, path)
  val isRoot: Boolean = Paths.isRoot(fullPath)

  def hasEntry(name: String): Boolean = findEntry(name).isDefined

  def findEntry(name: String, path: String = ""): Option[FileEntry] = {
    @tailrec
    def findDirRec(currentDir: Dir, pathSegments: List[String]): Option[FileEntry] =
      if (pathSegments.isEmpty) currentDir.findEntry(name)
      else currentDir.findEntry(pathSegments.head) match {
        case Some(nextDir: Dir) => findDirRec(nextDir, pathSegments.tail)
        case _ => None
      }

    if (path.isEmpty)
      entries.find(entry => entry.name.equals(name))
    else
      findDirRec(this, Paths.splitAndCollapse(path))
  }

  def addEntry(entry: FileEntry, path: String = ""): Dir = {
    def addEntryRec(currentDir: Dir, pathSegments: List[String]): Dir = {
      if (pathSegments.isEmpty) currentDir.addEntry(entry)
      else {
        val NextSegment: String = pathSegments.head

        currentDir.copy(entries = currentDir.entries.map {
          case nextDir@Dir(NextSegment, _, _) =>
            addEntryRec(nextDir, pathSegments.tail)
          case entry => entry
        })
      }
    }

    if (path.isEmpty) {
      copy(entries = entries :+ entry.withPath(fullPath))
    } else
      addEntryRec(this, Paths.splitAndCollapse(path))
  }

  def deleteEntry(name: String, path: String = ""): Dir = {
    def deleteEntryRec(currentDir: Dir, pathSegments: List[String]): Dir =
      pathSegments match {
        case List() => currentDir.deleteEntry(name)
        case _ =>
          val NextSegment: String = pathSegments.head

          currentDir.copy(entries = currentDir.entries.map {
            case nextDir@Dir(NextSegment, _, _) => deleteEntryRec(nextDir, pathSegments.tail)
            case entry => entry
          })
      }

    if (path.isEmpty)
      copy(entries = entries.filterNot(_.name.equals(name)))
    else
      deleteEntryRec(this, Paths.splitAndCollapse(path))
  }

  override def withPath(path: String): FileEntry = copy(path = path)
}

object RootDir extends Dir("", "") {}
