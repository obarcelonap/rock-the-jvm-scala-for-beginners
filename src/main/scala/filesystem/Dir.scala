package filesystem

case class Dir(
                name: String,
                parent: Dir = RootDir,
                children: List[Dir] = List()
              ) {}

object RootDir extends Dir("root") {}
