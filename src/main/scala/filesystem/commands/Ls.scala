package filesystem.commands

import filesystem.State

object Ls extends Command {
  override val NAME: String = "ls"
  override def apply(state: State, args: List[String]): State =
    if (state.cwd.entries.isEmpty) state.cleanOut()
    else state.out(
      state.cwd.entries
        .map(dir => dir.name)
        .mkString("\t"))
}
