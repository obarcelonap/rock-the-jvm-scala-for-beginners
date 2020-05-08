package filesystem.commands

import filesystem.{Dir, File, State}

object Touch extends Command {
  override val NAME: String = "touch"

  override def apply(state: State, args: List[String]): State =
    if (args.isEmpty) state.out("usage: touch filename")
    else {
      val filename = args.head
      if (state.cwd.hasEntry(filename)) state.out(s"touch: ${filename}: File exists")
      else {
        val newRoot = state.root.addEntry(File(filename), state.cwd.absolutePath)

        state
          .changeRoot(newRoot)
          .cleanOut()
      }
    }
}
