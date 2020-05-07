package filesystem.commands

import filesystem.{Dir, File, RootDir, State}
import org.scalatest.Inside
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class TouchSpec extends AnyFunSpec with Matchers with Inside {

  describe("apply") {
    it("should fail when name is not specified as argument") {
      val state = State()

      val newState = Touch(state)

      newState.out should startWith("usage")
    }
    it("should fail when name is already a dir entry in the cwd") {
      val rootDir = RootDir.addEntry(Dir("testdir"))
      val state = State(root = rootDir, cwd = rootDir)

      val newState = Touch(state, List("testdir"))

      newState.out should startWith("touch")
    }
    it("should fail when name is already a file entry in the cwd") {
      val rootDir = RootDir.addEntry(File("testfile"))
      val state = State(root = rootDir, cwd = rootDir)

      val newState = Touch(state, List("testfile"))

      newState.out should startWith("touch")
    }
    it("should create an empty file at root") {
      val state = State()

      val newState = Touch(state, List("emptyfile"))

      inside(newState) {
        case State(_, cwd, root, out) =>
          out should be(empty)
          cwd should be(root)
          inside(root) {
            case Dir(_, _, List(File(filename, path, content))) =>
              filename should be("emptyfile")
              path should be("/")
              content should be(empty)
          }
      }
    }
    it("should create an empty file inside a folder") {
      val firstLevelDir = Dir("1st-level")
      val rootDir = RootDir.copy(entries = List(firstLevelDir))
      val state = State(root = rootDir, cwd = firstLevelDir)

      val newState = Touch(state, List("emptyfile"))

      inside(newState) {
        case State(_, cwd, root, out) =>
          out should be(empty)
          inside(root) {
            case Dir(_, _, List(Dir(dirName, _, List(File(filename, path, content))))) =>
              dirName should be("1st-level")
              filename should be("emptyfile")
              path should be("/1st-level")
              content should be(empty)
          }
      }
    }
  }
}
