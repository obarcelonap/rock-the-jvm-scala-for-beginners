package filesystem.commands

import filesystem.{File, RootDir, State}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class CatSpec extends AnyFunSpec with Matchers{

  describe("apply") {
    it("fails with usage when no args") {
      val state = State()

      val newState = Cat(state)

      newState.out should be ("usage: cat file")
    }
    it("fails when path is not found") {
      val state = State()

      val newState = Cat(state, List("whatever"))

      newState.out should startWith ("cat: no such file")
    }
    it("outputs the file content") {
      val rootDir = RootDir.addEntry(File("a.txt", "/", "my content"))
      val state = State(root = rootDir, cwd = rootDir)

      val newState = Cat(state, List("a.txt"))

      newState.out should be ("my content")
    }
  }
}
