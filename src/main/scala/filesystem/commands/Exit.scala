package filesystem.commands

import filesystem.State

object Exit {
  def apply(state: State = State()): State = state.copy(running = false, out = "Made with ❤")
}
