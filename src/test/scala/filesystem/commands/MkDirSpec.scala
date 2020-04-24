package filesystem.commands

import filesystem.{Dir, RootDir, State}
import org.scalatest.Inside
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class MkDirSpec extends AnyFunSpec with Inside with Matchers {
  describe("apply") {
    it("should output usage when args distinct than 1") {
      val state = State()

      val newState = MkDir(state)

      newState.out should startWith("usage")
    }
    it("should create a dir on root") {
      val state = State(cwd = RootDir)

      val newState = MkDir(state, List("test-dir"))

      inside(newState.cwd) {
        case Dir(root, _, List(Dir(name, parent, children))) =>
          root should be("root")
          name should be("test-dir")
          parent should be(RootDir)
          children should be(empty)
      }
    }
    it("should create a dir on a folder") {
      val parentDir = Dir("1st-level")
      val state = State(cwd = parentDir)

      val newState = MkDir(state, List("2nd-level"))

      inside(newState.cwd) {
        case Dir(parentDir.name, _, List(Dir(name, parent, children))) =>
          name should be("2nd-level")
          parent should be(parentDir)
          children should be(empty)
      }
    }
    it("should create a dir on a folder with other children") {
      val parentDir = RootDir :+ "1st"
      val state = State(cwd = parentDir)

      val newState = MkDir(state, List("2nd"))

      inside(newState.cwd) {
        case Dir(root, _, List(Dir(first, _, _), Dir(second, _, _))) =>
          root should be("root")
          first should be("1st")
          second should be("2nd")
      }
    }
    it("should output error when dir already exists") {
      val parentDir = RootDir :+ "1st"
      val state = State(cwd = parentDir)

      val newState = MkDir(state, List("1st"))

      inside(newState) {
        case State(_, cwd, out) =>
          cwd should be(parentDir)
          out should be("mkdir: 1st: File exists")
      }
    }
  }
}
