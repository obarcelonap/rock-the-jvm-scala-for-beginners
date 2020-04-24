package filesystem.commands

import filesystem.{Dir, RootDir, State}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class LsSpec extends AnyFunSpec with Matchers {

  describe("apply") {
    it("should output nothing when cwd is empty") {
      val state = State()

      val newState = Ls(state)

      newState.out should be(empty)
    }
    it("should list dirs in cwd separated by tab") {
      val state = State(cwd = RootDir.copy(children = RootDir.children :+ Dir("one") :+ Dir("two")))

      val newState = Ls(state)

      newState.out should be("one\ttwo")
    }
  }

}
