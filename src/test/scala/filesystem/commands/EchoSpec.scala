package filesystem.commands

import filesystem.{Dir, File, State}
import org.scalatest.Inside
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class EchoSpec extends AnyFunSpec with Inside with Matchers  {

  describe("apply") {
    it("outputs nothing when there is no message") {
      val state = State()

      val newState = Echo(state)

      newState.out should be (empty)
    }
    it("prints by console the message") {
      val state = State()

      val newState = Echo(state, List("hello", "world"))

      newState.out should be ("hello world")
    }
  }
}
