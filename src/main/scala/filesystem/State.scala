package filesystem

case class State(
                  running: Boolean = true,
                  cwd: Dir = RootDir,
                  out: String = "",
                ) {
  def out(message: String): State = copy(out = message)
  def cleanOut(): State = copy(out = "")
}
