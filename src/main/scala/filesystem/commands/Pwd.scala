package filesystem.commands

import filesystem.State

object Pwd extends Command {
  override val NAME: String = "pwd"
  override def apply(state: State, args: List[String]): State = state.out(state.cwd.absolutePath)
}
