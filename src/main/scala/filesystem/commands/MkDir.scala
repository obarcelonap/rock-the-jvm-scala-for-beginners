package filesystem.commands

import filesystem.{Dir, State}

object MkDir extends Command {
  override val NAME = "mkdir"


  override def apply(state: State = State(), args: List[String] = List()): State = args match {
    case List(directory) =>
      if (state.cwd.hasEntry(directory))
        state.out(s"mkdir: ${directory}: File exists")
      else
        mkdir(state, directory)
    case _ => state.out("usage: mkdir directory")
  }

  private def mkdir(state: State, directory: String): State = {
    val newRoot = state.root.addEntry(Dir(directory), state.cwd.absolutePath)

    state
      .changeRoot(newRoot)
      .cleanOut()
  }
}