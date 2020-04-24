package filesystem.commands

import filesystem.{Dir, State}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class PwdSpec extends AnyFunSpec with Matchers {

  describe("apply") {
    it("should show slash when cwd is root dir") {
      val state = State()

      val newState = Pwd(state)

      newState.out should be("/")
    }
    it("should show dirs until root separated by slash when cwd is a nested folder") {
      val level1 = Dir("level1") :+ "level2"
      val state = State(cwd = level1.children.head)

      val newState = Pwd(state)

      newState.out should be("/level1/level2")
    }
  }
}
