package filesystem.commands

import filesystem.{Dir, File, State}

object Touch extends Command {
  override val NAME: String = "touch"

  override def apply(state: State, args: List[String]): State =
    if (args.isEmpty) state.out("usage: touch filename")
    else {
      val filename = args.head
      if (state.cwd.hasChild(filename)) state.out(s"touch: ${filename}: File exists")
      else {
        val newRoot = state.root.addEntry(File(filename), state.cwd.fullPath)
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
