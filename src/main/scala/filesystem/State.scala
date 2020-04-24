package filesystem

case class State(
                  running: Boolean = true,
                  cwd: Dir = RootDir,
                  out: String = "",
                ) {}
