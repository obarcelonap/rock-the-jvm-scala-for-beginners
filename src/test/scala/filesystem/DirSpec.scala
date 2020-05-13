package filesystem

import org.scalatest.{Inside, OptionValues}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class DirSpec extends AnyFunSpec with Matchers with Inside with OptionValues {

  describe("findEntry") {
    it("should return empty when no entries") {
      val entry = RootDir.findEntry("/anything")

      entry should be(empty)
    }

    it("should return empty when entries does not match the initial path") {
      val dir = RootDir.copy(entries = List(
        Dir("a", "/", List(
          Dir("b", "/a"),
          Dir("c", "/a"),
        )),
      ))

      val entry = dir.findEntry("/b/c")

      entry should be(empty)
    }

    it("should return empty when entries does not match the path after some match") {
      val dir = RootDir.copy(entries = List(
        Dir("a", "/", List(
          Dir("b", "/a"),
          Dir("c", "/a"),
        )),
      ))

      val entry = dir.findEntry("/a/not-expected/b")

      entry should be(empty)
    }

    it("should return the dir when is found nested at one level") {
      val dir = RootDir.copy(entries = List(
        Dir("a", "/", List(
          Dir("b", "/a"),
          Dir("c", "/a"),
        )),
      ))

      val entry = dir.findEntry("/a")

      entry.value.name should be("a")
    }

    it("should return the dir when is found nested at two levels") {
      val dir = RootDir.copy(entries = List(
        Dir("a", "/", List(
          Dir("b", "/a"),
          Dir("c", "/a"),
        )),
      ))

      val entry = dir.findEntry("/a/b")

      entry.value.name should be("b")
    }
  }

  describe("addEntry") {
    it("adds a dir at root when path is empty") {
      val newRoot = RootDir.addEntry(Dir("a", "/"))

      inside(newRoot) {
        case Dir(_, _, List(Dir(name, path, _))) =>
          name should be ("a")
          path should be ("/")
      }
    }

    it("does nothing at root when path is not found") {
      val newRoot = RootDir.addEntry(Dir("a", "/does/not/exist"))

      inside(newRoot) {
        case Dir(_, _, entries) =>
          entries should be (empty)
      }
    }

    it("does nothing when path is not found") {
      val newRoot = Dir("a").addEntry(Dir("b", "/does/not/exist"))

      inside(newRoot) {
        case Dir(_, _, entries) =>
          entries should be (empty)
      }
    }

    it("adds an entry in a nested dir when path has a value") {
      val newRoot = RootDir
        .addEntry(Dir("a", "/"))
        .addEntry(Dir("b", "/a"))


      inside(newRoot) {
        case Dir(name1, path1, List(Dir(name2, path2, List(Dir(name3, path3, _))))) =>
          name1 should be ("")
          path1 should be ("/")
          name2 should be ("a")
          path2 should be ("/")
          name3 should be ("b")
          path3 should be ("/a")
      }
    }
  }

}
