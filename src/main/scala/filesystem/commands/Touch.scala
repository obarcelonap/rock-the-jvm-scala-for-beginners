package filesystem.commands

import filesystem.Paths.{absolutePath, relativePath}
import filesystem.{Dir, File, Paths, State}

object Touch extends Command {
  override val NAME: String = "touch"

  override def apply(state: State = State(), args: List[String] = List()): State = args match {
    case List(absolutePath(path)) => touch(state, path)
    case List(relativePath(path)) => touch(state, Paths.concat(state.cwd.fullPath, path))
    case _ => state.out("usage: touch file")
  }

  private def touch(state: State, path: String): State = {
    val filePath = Paths.collapse(path)
    if (state.root.findEntry(filePath).isDefined) {
      return state.out(s"touch: $filePath: File exists")
    }

    val newRoot = state.root.addEntry(File.fromFullPath(filePath))

    state
      .changeRoot(newRoot)
      .cleanOut()
  }
}
