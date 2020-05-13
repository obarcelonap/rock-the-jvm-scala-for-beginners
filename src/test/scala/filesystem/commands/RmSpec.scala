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
      val rootDir = RootDir.copy(entries = List(
        Dir("a", "/"),
        Dir("b", "/"),
      ))
      val state = State(root = rootDir, cwd = rootDir)

      val newState = Rm(state, List("a"))

      inside(newState.root) {
        case Dir(_, _, entries) =>
          entries should be(List(Dir("b", "/")))
      }
    }
    it ("should delete files at root") {
      val rootDir = RootDir.copy(entries = List(
        File("a", "/"),
        File("b", "/"),
      ))
      val state = State(root = rootDir, cwd = rootDir)

      val newState = Rm(state, List("a"))

      inside(newState.root) {
        case Dir(_, _, entries) =>
          entries should be(List(File("b", "/")))
      }
    }
    it ("should delete dirs at child folder") {
      val cwdDir = Dir("b", "/a", List(
        Dir("c", "/a/b"),
        Dir("d", "/a/b"),
      ))
      val rootDir = RootDir.copy(entries = List(
        Dir("a", "/", List(
          cwdDir,
        ))
      ))
      val state = State(root = rootDir, cwd = cwdDir)

      val newState = Rm(state, List("c"))

      newState.root.entries should be(List(
        Dir("a", "/", List(
          Dir("b", "/a", List(
            Dir("d", "/a/b"),
          )),
        )),
      ))
    }
    it ("should delete files at child folder") {
      val cwdDir = Dir("b", "/a", List(
        File("c", "/a/b"),
        Dir("d", "/a/b"),
      ))
      val rootDir = RootDir.copy(entries = List(
        Dir("a", "/", List(
          cwdDir,
        ))
      ))
      val state = State(root = rootDir, cwd = cwdDir)

      val newState = Rm(state, List("c"))

      newState.root.entries should be(List(
        Dir("a", "/", List(
          Dir("b", "/a", List(
            Dir("d", "/a/b"),
          )),
        )),
      ))
    }
    it ("should delete dirs when absolute path") {
      val cwdDir = Dir("c", "/")
      val rootDir = RootDir.copy(entries = List(
        Dir("a", "/", List(
          Dir("b", "/a")
        )),
        cwdDir,
      ))
      val state = State(root = rootDir, cwd = cwdDir)

      val newState = Rm(state, List("/a/b"))

      newState.root.entries should be(List(
        Dir("a", "/"),
        Dir("c", "/"),
      ))
    }
    it ("should delete dirs when relative path") {
      val cwdDir = Dir("c", "/")
      val rootDir = RootDir.copy(entries = List(
        Dir("a", "/", List(
          Dir("b", "/a")
        )),
        cwdDir,
      ))
      val state = State(root = rootDir, cwd = cwdDir)

      val newState = Rm(state, List("../a/b"))

      newState.root.entries should be(List(
        Dir("a", "/"),
        Dir("c", "/"),
      ))
    }
  }
}
