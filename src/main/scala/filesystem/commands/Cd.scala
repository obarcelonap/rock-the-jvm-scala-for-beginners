package filesystem.commands

import filesystem.Paths.{absolutePath, relativePath}
import filesystem.commands.MkDir.mkdir
import filesystem.{Dir, Paths, State}

object Cd extends Command {
  override val NAME: String = "cd"

  override def apply(state: State, args: List[String]): State = args match {
    case List(absolutePath(path)) => cd(state, path)
    case List(relativePath(path)) => cd(state, Paths.concat(state.cwd.fullPath, path))
    case _ => state.out("usage: cd directory")
  }

  private def cd(state: State, path: String): State = {
    val directoryPath = Paths.collapse(path)
    if (Paths.isRoot(directoryPath)) {
      return state.copy(cwd = state.root).cleanOut()
    }

    state.root.findEntry(directoryPath) match {
      case Some(dir: Dir) => state.copy(cwd = dir).cleanOut()
      case Some(_) => state.out(s"cd: not a directory: ${Paths.segments(directoryPath).last}")
      case _ => state.out(s"cd: no such file or directory: ${Paths.segments(directoryPath).last}")
    }
  }
}
