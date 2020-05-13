package filesystem.commands

import filesystem.{File, Paths, State}

object Echo extends Command {
  override val NAME: String = "echo"

  override def apply(state: State, args: List[String]): State = args match {
    case contentParts :+ ">" :+ filePath => addOrReplaceFile(state, filePath, makeContent(contentParts))
    case contentParts :+ ">>" :+ filePath => appendOrAddFile(state, filePath, makeContent(contentParts))
    case _ => state.out(makeContent(args))
  }

  private def addOrReplaceFile(state: State, filePath: String, content: String): State =
    state.changeRoot(
      state.root
        .deleteEntry(filePath)
        .addEntry(File.fromFullPath(filePath, content))
    )
    .cleanOut()

  private def appendOrAddFile(state: State, filePath: String, content: String): State = {
    val newContent = state.root.findEntry(filePath) match {
      case Some(File(_, _, prevContent)) => makeContent(prevContent, content)
      case _ => content
    }
    addOrReplaceFile(state, filePath, newContent)
  }

  private def makeContent(args: List[String]): String = makeContent(args:_*)
  private def makeContent(args: String*): String = args.mkString(" ")
}
