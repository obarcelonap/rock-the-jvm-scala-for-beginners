package filesystem.commands

import filesystem.State

object Exit extends Command {
  override val NAME = "exit"

  override def apply(state: State = State(), args: List[String] = List()): State =
    state.copy(running = false)
      .out("Made with ❤")
}
