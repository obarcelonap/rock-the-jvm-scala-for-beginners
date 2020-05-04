package filesystem.commands

import filesystem.{Dir, Paths, State}

object Cd extends Command {
  override val NAME: String = "cd"

  override def apply(state: State, args: List[String]): State =
    if (args.isEmpty || Paths.isRoot(args.head)) state.copy(cwd = state.root).cleanOut()
    else {
      val absolutePath =
        if (Paths.startsFromRoot(args.head)) args.head
        else s"${state.cwd.fullPath}/${args.head}"

      val segments = Paths.collapse(Paths.splitInSegments(absolutePath))
      if (segments.isEmpty)
        return state.copy(cwd = state.root).cleanOut()
      if (segments.equals(Paths.splitInSegments(state.cwd.path)))
        return state

      val path = segments.init
      val directory = segments.last

      state.root.findChild(directory, path.mkString("/")) match {
        case Some(dir: Dir) => state.copy(cwd = dir).cleanOut()
        case Some(_) => state.out(s"cd: not a directory: $directory")
        case _ => state.out(s"cd: no such file or directory: $directory")
      }
    }
}
