package filesystem.commands

import filesystem.State

object NotFound {
  def apply(state: State = State()): State = state.copy(out = s"Command not found: ${state.command}")
}
