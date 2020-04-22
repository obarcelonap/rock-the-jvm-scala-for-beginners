package fp

import fp.Functions.{adder, concat}
import org.scalatest.funspec.AnyFunSpec

class FunctionsSpec extends AnyFunSpec {

  describe("concat") {
    it("should concat two strings") {
      val concatenated = concat("foo", "bar")

      assert(concatenated == "foobar")
    }
  }
  describe("adder") {
    it("creates a function which adds by the specified value") {
      val result = adder(100)(50)

      assert(result == 150)
    }
  }
}
