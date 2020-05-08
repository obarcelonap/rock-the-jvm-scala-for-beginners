package filesystem.commands

import filesystem.State

object Echo extends Command {
  override val NAME: String = "echo"

  override def apply(state: State, args: List[String]): State = state.out(args.mkString(" "))
}
