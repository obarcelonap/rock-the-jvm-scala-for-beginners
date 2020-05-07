package filesystem.commands

import filesystem.{Dir, File, RootDir, State}
import org.scalatest.Inside
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class RmSpec extends AnyFunSpec with Inside with Matchers {

  describe("apply") {
    it("should show usage when no args") {
      val state = State()

      val newState = Rm(state)

      newState.out should startWith("usage")
    }
    it ("should delete dirs at root") {
      val rootDir = RootDir.addEntry(Dir("a"))
      val state = State(root = rootDir, cwd = rootDir)

      val newState = Rm(state, List("a"))

      inside(newState.root) {
        case Dir(_, _, entries) =>
          entries should be(empty)
      }
    }
    it ("should delete files at root") {
      val rootDir = RootDir
        .addEntry(File("a"))
        .addEntry(File("b"))
      val state = State(root = rootDir, cwd = rootDir)

      val newState = Rm(state, List("a"))

      inside(newState.root) {
        case Dir(_, _, entries) =>
          entries should be(List(File("b", "/")))
      }
    }
  }

}
