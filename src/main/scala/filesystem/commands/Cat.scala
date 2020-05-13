package filesystem.commands

import filesystem.Paths.{absolutePath, relativePath}
import filesystem.{File, Paths, State}

object Cat extends Command {
  override val NAME: String = "cat"

  override def apply(state: State = State(), args: List[String] = List()): State = args match {
    case List(absolutePath(path)) => cat(state, path)
    case List(relativePath(path)) => cat(state, Paths.concat(state.cwd.fullPath, path))
    case _ => state.out("usage: cat file")
  }

  def cat(state: State, filePath: String): State = state.root.findEntry(filePath) match {
    case Some(File(_, _, content)) => state.out(content)
    case Some(_) => state.out(s"cat: not a file: ${Paths.segments(filePath).last}")
    case _ => state.out(s"cat: no such file: ${Paths.segments(filePath).last}")
  }
}
