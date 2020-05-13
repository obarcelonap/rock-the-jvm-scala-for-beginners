package filesystem

case class State(
                  running: Boolean = true,
                  cwd: Dir = RootDir,
                  root: Dir = RootDir,
                  out: String = "",
                ) {
  def out(message: String): State = copy(out = message)
  def cleanOut(): State = copy(out = "")
  def changeRoot(newRoot: Dir): State = {
    val newCwd =
      if (cwd.isRoot) newRoot
      else newRoot.findEntry(cwd.fullPath) match {
        case Some(dir: Dir) => dir
        case _ => throw new RuntimeException("error: cannot find cwd in the filesystem")
      }

    copy(root = newRoot, cwd = newCwd)
  }
}
