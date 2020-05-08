package filesystem.commands

import filesystem.{Dir, State}

object Rm extends Command {
  override val NAME: String = "rm"

  override def apply(state: State, args: List[String]): State =
    if (args.length != 1) state.out("usage: rm file")
    else {
      val newRoot = state.root.deleteEntry(args.head, state.cwd.absolutePath)

      state
        .changeRoot(newRoot)
        .cleanOut()
    }
}
