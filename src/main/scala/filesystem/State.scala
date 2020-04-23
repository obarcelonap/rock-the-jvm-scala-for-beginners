package filesystem

case class State(
                  running: Boolean = true,
                  command: String = "",
                  args: List[String] = List(),
                  out: String = "") {}
