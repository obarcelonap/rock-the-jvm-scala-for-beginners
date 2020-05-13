package filesystem.commands

import filesystem.Paths.{absolutePath, relativePath}
import filesystem.commands.Rm.rm
import filesystem.{Dir, Paths, State}

object MkDir extends Command {
  override val NAME = "mkdir"


  override def apply(state: State = State(), args: List[String] = List()): State = args match {
    case List(absolutePath(path)) => mkdir(state, path)
    case List(relativePath(path)) => mkdir(state, Paths.concat(state.cwd.fullPath, path))
    case _ => state.out("usage: mkdir file")
  }

  private def mkdir(state: State, path: String): State = {
    val directoryPath = Paths.collapse(path)
    if (state.root.findEntry(directoryPath).isDefined) {
      return state.out(s"mkdir: ${Paths.segments(directoryPath).last}: File exists")
    }

    val newRoot = state.root.addEntry(Dir.fromFullPath(directoryPath))

    state
      .changeRoot(newRoot)
      .cleanOut()
  }
}