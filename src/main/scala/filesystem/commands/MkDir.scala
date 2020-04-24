package filesystem.commands

import filesystem.{Dir, State}

object MkDir extends Command {
  override val NAME = "mkdir"

  override def apply(state: State = State(), args: List[String] = List()): State =
    if (args.length != 1) state.copy(out = "usage: mkdir directory")
    else {
      val directory = args.head
      if (state.cwd.children.exists(dir => dir.name == directory)) state.copy(out = s"mkdir: ${directory}: File exists")
      else state.copy(cwd = state.cwd.copy(children = state.cwd.children :+ Dir(directory, state.cwd)), out = "")
    }
}
