package filesystem.commands

import filesystem.State

object NotFound {
  def apply(name: String): Command = new NotFound(name)
}

class NotFound(val name: String) extends Command {
  override val NAME: String = "notfound"
  override def apply(state: State, args: List[String]): State =
    state.out(s"command not found: ${name}")
}
