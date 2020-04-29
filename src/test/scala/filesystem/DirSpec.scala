package filesystem

import org.scalatest.{Inside, OptionValues}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class DirSpec extends AnyFunSpec with Matchers with Inside with OptionValues {

  describe("findChildDir with path param") {
    it("should return empty when no children") {
      val dir = Dir("empty")

      val child = dir.findChildDir("anything", "/does/not/matter")

      child should be(empty)
    }

    it("should return empty when children does not match the initial path") {
      val dir = Dir("a", "/", List(Dir("b"), Dir("c")))

      val child = dir.findChildDir("anything", "/does/not/matter")

      child should be(empty)
    }

    it("should return empty when children does not match the path after some match") {
      val dir = Dir("a", "/", List(Dir("b"), Dir("c")))

      val child = dir.findChildDir("anything", "/a/not-expected")

      child should be(empty)
    }

    it("should return the dir when is found nested at one level") {
      val expected = Dir("expected", "/a/c/")
      val dir = Dir("a", "/",
        List(Dir("b"), Dir("c", "/a/",
          List(expected))))

      val child = dir.findChildDir("expected", "/c/")

      child should be(Some(expected))
    }

    it("should return the dir when is found nested at two levels") {
      val expected = Dir("expected", "/a/b/c/")
      val dir = Dir("a", "/",
        List(Dir("b", "/a/",
          List(Dir("c", "/a/b/",
            List(expected))))))

      val child = dir.findChildDir("expected", "/b/c/")

      child should be(Some(expected))
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
        case Dir(_, _, children) =>
          children should be (empty)
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
        case Dir(_, _, children) =>
          children should be (empty)
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
