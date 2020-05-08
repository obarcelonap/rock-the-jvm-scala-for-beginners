package filesystem.commands

import filesystem.{Dir, File, State}

object Touch extends Command {
  override val NAME: String = "touch"

  override def apply(state: State = State(), args: List[String] = List()): State = args match {
    case List(filename) =>
      if (state.cwd.hasEntry(filename))
        state.out(s"touch: ${filename}: File exists")
      else
        touch(state, filename)
    case _ => state.out("usage: touch filename")
  }

  private def touch(state: State, filename: String): State = {
    val newRoot = state.root.addEntry(File(filename), state.cwd.absolutePath)

    state
      .changeRoot(newRoot)
      .cleanOut()
  }
}
