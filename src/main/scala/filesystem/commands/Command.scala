package filesystem.commands

import filesystem.State

trait Command {
  val NAME: String;
  def apply(state: State = State(), args: List[String] = List()): State
}

object Command {
  def get(name: String): Command = name match {
    case Exit.NAME => Exit
    case MkDir.NAME => MkDir
    case Ls.NAME => Ls
    case Pwd.NAME => Pwd
    case Touch.NAME => Touch
    case Cd.NAME => Cd
    case _ => NotFound(name)
  }
}
