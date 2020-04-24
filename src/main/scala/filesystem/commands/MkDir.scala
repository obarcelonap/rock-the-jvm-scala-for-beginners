package filesystem.commands

import filesystem.State

object MkDir extends Command {
  override val NAME = "mkdir"

  override def apply(state: State = State(), args: List[String] = List()): State =
    if (args.length != 1) state.out("usage: mkdir directory")
    else {
      val directory = args.head
      if (state.cwd.hasChild(directory)) state.out(s"mkdir: ${directory}: File exists")
      else state
        .copy(cwd = state.cwd :+ directory)
        .cleanOut()
    }
}
