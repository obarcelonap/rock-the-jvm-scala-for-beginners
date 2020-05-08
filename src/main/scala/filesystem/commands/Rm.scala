package filesystem.commands

import filesystem.{Dir, State}

object Rm extends Command {
  override val NAME: String = "rm"

  override def apply(state: State, args: List[String]): State =
    if (args.length != 1) state.out("usage: rm file")
    else {
      val newRoot = state.root.deleteEntry(args.head, state.cwd.absolutePath)
      val newCwd = if (state.cwd.isRoot) newRoot
      else newRoot.findEntry(state.cwd.name, state.cwd.path) match {
        case Some(dir: Dir) => dir
        case _ => throw new RuntimeException("error: cannot find cwd in the filesystem")
      }

      state
        .copy(root = newRoot, cwd = newCwd)
        .cleanOut()
    }
}
