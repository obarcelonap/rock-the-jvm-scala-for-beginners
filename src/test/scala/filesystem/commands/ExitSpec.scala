package filesystem.commands

import filesystem.State
import org.scalatest.Inside
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class ExitSpec extends AnyFunSpec with Inside with Matchers {

  describe("apply") {
    it("sets running state to false and says good bye") {
      val state = State()

      val newState = Exit(state)

      inside(newState) {
        case State(running, _, _, out) =>
          running should be(false)
          out should not be (empty)
      }
    }
  }
}
