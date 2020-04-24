package filesystem.commands

import filesystem.State
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class NotFoundSpec extends AnyFunSpec with Matchers {

  describe("apply") {
    it("should output the unknown command name") {
      val state = State()

      val newState = NotFound("wtf")(state)

      newState.out should include("wtf")
    }
  }

}
