package filesystem.commands

import filesystem.{Dir, State}

object MkDir extends Command {
  override val NAME = "mkdir"

  override def apply(state: State = State(), args: List[String] = List()): State =
    if (args.length != 1) state.out("usage: mkdir directory")
    else {
      val directory = args.head
      if (state.cwd.hasChild(directory)) state.out(s"mkdir: ${directory}: File exists")
      else {
        val newRoot = state.root.addEntry(new Dir(directory), state.cwd.fullPath)
        val newCwd = if (state.cwd.isRoot) newRoot
          else newRoot.findChild(state.cwd.name, state.cwd.path) match {
            case Some(dir: Dir) => dir
            case _ => throw new RuntimeException("error: cannot find cwd in the filesystem")
          }

        state
          .copy(root = newRoot, cwd = newCwd)
          .cleanOut()
      }
    }
}