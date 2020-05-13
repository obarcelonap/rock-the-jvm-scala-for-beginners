package filesystem.commands

import filesystem.{Dir, File, RootDir, State}
import org.scalatest.Inside
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class CdSpec extends AnyFunSpec with Matchers with Inside {

  describe("apply") {
    it("sets cwd to root when no args") {
      val state = State()

      val newState = Cd(state)

      inside(newState) {
        case State(_, cwd, root, _) =>
          cwd should be (root)
      }
    }
    it("fails when directory is not found") {
      val state = State()

      val newState = Cd(state, List("a"))

      inside(newState) {
        case State(_, _, _, out) =>
          out should be("cd: no such file or directory: a")
      }
    }
    it("fails when arg is not a directory") {
      val rootDir = RootDir.addEntry(File("a.txt"))
      val state = State(root = rootDir, cwd = rootDir)

      val newState = Cd(state, List("a.txt"))

      inside(newState) {
        case State(_, _, _, out) =>
          out should be("cd: not a directory: a.txt")
      }
    }
    it("sets cwd to the specified relative dir") {
      val rootDir = RootDir.addEntry(Dir("a", "/"))
      val state = State(root = rootDir, cwd = rootDir)

      val newState = Cd(state, List("a"))

      inside(newState) {
        case State(_, Dir(cwdName, cwdPath, _), _, _) =>
          cwdName should be ("a")
          cwdPath should be ("/")
      }
    }
    it("sets cwd to the specified relative dir at 2nd level") {
      val rootDir = RootDir
        .addEntry(Dir("a"))
        .addEntry(Dir("b", "/a"))
      val state = State(root = rootDir, cwd = rootDir)

      val newState = Cd(state, List("a/b"))

      inside(newState) {
        case State(_, Dir(cwdName, cwdPath, _), _, _) =>
          cwdName should be ("b")
          cwdPath should be ("/a")
      }
    }
  }
}
