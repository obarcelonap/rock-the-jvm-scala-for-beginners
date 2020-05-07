package filesystem

import org.scalatest.{Inside, OptionValues}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class DirSpec extends AnyFunSpec with Matchers with Inside with OptionValues {

  describe("findEntry") {
    it("should return empty when no entries") {
      val dir = Dir("empty")

      val entry = dir.findEntry("anything", "/does/not/matter")

      entry should be(empty)
    }

    it("should return empty when entries does not match the initial path") {
      val dir = Dir("a", "/", List(Dir("b"), Dir("c")))

      val entry = dir.findEntry("anything", "/does/not/matter")

      entry should be(empty)
    }

    it("should return empty when entries does not match the path after some match") {
      val dir = Dir("a", "/", List(Dir("b"), Dir("c")))

      val entry = dir.findEntry("anything", "/a/not-expected")

      entry should be(empty)
    }

    it("should return the dir when is found nested at one level") {
      val expected = Dir("expected", "/a/c/")
      val dir = Dir("a", "/",
        List(Dir("b"), Dir("c", "/a/",
          List(expected))))

      val entry = dir.findEntry("expected", "/c/")

      entry should be(Some(expected))
    }

    it("should return the dir when is found nested at two levels") {
      val expected = Dir("expected", "/a/b/c/")
      val dir = Dir("a", "/",
        List(Dir("b", "/a/",
          List(Dir("c", "/a/b/",
            List(expected))))))

      val entry = dir.findEntry("expected", "/b/c/")

      entry should be(Some(expected))
    }
  }

  describe("addEntry") {
    it("adds a dir at root when path is empty") {
      val newRoot = RootDir.addEntry(Dir("a"))

      inside(newRoot) {
        case Dir(_, _, List(Dir(name, path, _))) =>
          name should be ("a")
          path should be ("/")
      }
    }

    it("does nothing at root when path is not found") {
      val newRoot = RootDir.addEntry(Dir("a"), "/does/not/exist")

      inside(newRoot) {
        case Dir(_, _, entries) =>
          entries should be (empty)
      }
    }

    it("adds an entry at the same dir when path is empty") {
      val newRoot = Dir("a").addEntry(Dir("b"))

      inside(newRoot) {
        case Dir(_, _, List(Dir(name, path, _))) =>
          name should be ("b")
          path should be ("/a")
      }
    }

    it("does nothing when path is not found") {
      val newRoot = Dir("a").addEntry(Dir("b"), "/does/not/exist")

      inside(newRoot) {
        case Dir(_, _, entries) =>
          entries should be (empty)
      }
    }

    it("adds an entry in a nested dir when path has a value") {
      val newRoot = Dir("a")
        .addEntry(Dir("b"))
        .addEntry(Dir("c"), "/b")


      inside(newRoot) {
        case Dir(name1, path1, List(Dir(name2, path2, List(Dir(name3, path3, _))))) =>
          name1 should be ("a")
          path1 should be ("")
          name2 should be ("b")
          path2 should be ("/a")
          name3 should be ("c")
          path3 should be ("/a/b")
      }
    }
  }

}
