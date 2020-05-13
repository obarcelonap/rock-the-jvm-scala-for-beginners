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
      val state = State(root = RootDir, cwd = RootDir)

      val newState = MkDir(state, List("test-dir"))

      inside(newState.root) {
        case Dir(_, _, List(Dir(name, path, entries))) =>
          name should be("test-dir")
          path should be("/")
          entries should be(empty)
      }
    }
    it("should create a dir on a folder") {
      val firstLevelDir = Dir("1st-level")
      val rootDir = RootDir.copy(entries = List(firstLevelDir))
      val state = State(root = rootDir, cwd = firstLevelDir)

      val newState = MkDir(state, List("2nd-level"))

      inside(newState.root) {
        case Dir(_, _, List(Dir(_, _, List(Dir(name, path, entries))))) =>
          name should be("2nd-level")
          path should be("/1st-level")
          entries should be(empty)
      }
    }
    it("should create a dir on a folder with other entries") {
      val rootDir = RootDir.copy(entries = List(Dir("1st")))
      val state = State(root = rootDir, cwd = rootDir)

      val newState = MkDir(state, List("2nd"))

      inside(newState.root) {
        case Dir(_, _, List(Dir(first, _, _), Dir(second, _, _))) =>
          first should be("1st")
          second should be("2nd")
      }
    }
    it("should output error when dir already exists") {
      val rootDir = RootDir.copy(entries = List(Dir("1st", "/")))
      val state = State(root = rootDir, cwd = rootDir)

      val newState = MkDir(state, List("1st"))

      inside(newState) {
        case State(_, _, _, out) =>
          out should be("mkdir: 1st: File exists")
      }
    }
  }
}
