package filesystem

import scala.annotation.tailrec

case class Dir(name: String, path: String = "", children: List[FileEntry] = List())
  extends FileEntry {

  val fullPath: String = Paths.fullPath(name, path)
  val isRoot: Boolean = Paths.isRoot(fullPath)

  def hasChild(name: String): Boolean = findChild(name).isDefined

  def findChild(name: String): Option[FileEntry] = children.find(entry => entry.name.equals(name))

  def findChild(name: String, path: String = ""): Option[FileEntry] = {
    @tailrec
    def findDirRec(currentDir: Dir, pathSegments: List[String]): Option[FileEntry] =
      if (pathSegments.isEmpty) currentDir.findChild(name)
      else currentDir.findChild(pathSegments.head) match {
        case Some(nextDir: Dir) => findDirRec(nextDir, pathSegments.tail)
        case _ => None
      }

    findDirRec(this, Paths.splitInSegments(path))
  }

  def findChildDir(name: String, path: String = ""): Option[Dir] = findChild(name, path) match {
    case Some(dir: Dir) => Some(dir)
    case _ => None
  }

  def addEntry(entry: FileEntry, path: String = ""): Dir = {

    def newEntry(path: String) = entry match {
      case dir: Dir => dir.copy(path = path)
      case file: File => file.copy(path = path)
      case _ => entry
    }

    def addEntryRec(currentDir: Dir, pathSegments: List[String]): Dir = {
      if (pathSegments.isEmpty)
        currentDir.copy(children = currentDir.children :+ newEntry(currentDir.fullPath))
      else {
        val NextSegment: String = pathSegments.head

        currentDir.copy(children = currentDir.children.map {
          case nextDir@Dir(NextSegment, _, _) =>
            addEntryRec(nextDir, pathSegments.tail)
          case child => child
        })
      }
    }

    addEntryRec(this, Paths.splitInSegments(path))
  }
}

object RootDir extends Dir("") {}
