package filesystem

import scala.annotation.tailrec

case class Dir(name: String, path: String = "", entries: List[FileEntry] = List())
  extends FileEntry {

  val isRoot: Boolean = Paths.isRoot(fullPath)

  def hasEntry(name: String): Boolean = findEntry(name).isDefined

  def findEntry(path: String): Option[FileEntry] = {
    @tailrec
    def findEntryRec(currentDir: Dir, pathSegments: List[String]): Option[FileEntry] =
      pathSegments match {
        case entryName :: Nil => currentDir.entries.find(_.name.equals(entryName))
        case nextSegment :: moreSegments => currentDir.entries.find(_.name.equals(nextSegment)) match {
          case Some(nextDir: Dir) => findEntryRec(nextDir, moreSegments)
          case _ => None
        }
        case _ => None
      }

    findEntryRec(this, Paths.segments(path))
  }

  def addEntry(entry: FileEntry): Dir = {
    def addEntryRec(currentDir: Dir, pathSegments: List[String]): Dir = {
      pathSegments match {
        case List() => currentDir.copy(entries = currentDir.entries :+ entry)
        case _ =>
          val NextSegment: String = pathSegments.head

          currentDir.copy(entries = currentDir.entries.map {
            case nextDir@Dir(NextSegment, _, _) => addEntryRec(nextDir, pathSegments.tail)
            case entry => entry
          })
      }
    }

    addEntryRec(this, Paths.segments(entry.path))
  }

  def deleteEntry(path: String): Dir = {
    def deleteEntryRec(currentDir: Dir, pathSegments: List[String]): Dir = {
      pathSegments match {
        case entryName :: Nil => currentDir.copy(entries = currentDir.entries.filterNot(_.name.equals(entryName)))
        case _ =>
          val NextSegment: String = pathSegments.head

          currentDir.copy(entries = currentDir.entries.map {
            case nextDir@Dir(NextSegment, _, _) => deleteEntryRec(nextDir, pathSegments.tail)
            case entry => entry
          })
      }
    }

    deleteEntryRec(this, Paths.segments(path))
  }

}

object Dir {
  def fromFullPath(path: String): Dir = {
    val directoryPathSegments :+ directoryName = Paths.segments(path)
    Dir(directoryName, Paths.asPath(directoryPathSegments))
  }
}

object RootDir extends Dir("", "/") {}
