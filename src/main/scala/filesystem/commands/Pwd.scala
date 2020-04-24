package filesystem.commands

import filesystem.{Dir, RootDir, State}

import scala.annotation.tailrec

object Pwd extends Command {
  override val NAME: String = "pwd"
  override def apply(state: State, args: List[String]): State = {
    @tailrec
    def dirNamesUntilRoot(dir: Dir, accum: List[String] = List()): List[String] =
      if (dir == RootDir) accum
      else dirNamesUntilRoot(dir.parent, dir.name +: accum)

    state.out(
      dirNamesUntilRoot(state.cwd)
        .mkString("/", "/", ""))
  }
}
