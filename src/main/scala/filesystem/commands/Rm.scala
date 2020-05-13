package filesystem.commands

import filesystem.Paths.{absolutePath, relativePath}
import filesystem.{Dir, Paths, State}

object Rm extends Command {
  override val NAME: String = "rm"

  override def apply(state: State, args: List[String]): State = args match {
    case List(absolutePath(path)) => rm(state, path)
    case List(relativePath(path)) => rm(state, Paths.concat(state.cwd.fullPath, path))
    case _ => state.out("usage: rm file")
  }

  private def rm(state: State, path: String): State =
    state
      .changeRoot(state.root.deleteEntry(Paths.collapse(path)))
      .cleanOut()
}
