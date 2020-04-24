package filesystem

case class Dir(
                name: String,
                parent: Dir = RootDir,
                children: List[Dir] = List()
              ) {
  def :+(directory: String): Dir = copy(children = children :+ Dir(directory, this))
  def hasChild(directory: String): Boolean = children.exists(dir => dir.name == directory)
}

object RootDir extends Dir("root") {}
