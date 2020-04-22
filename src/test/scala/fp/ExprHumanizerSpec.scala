package fp

import org.scalatest.funspec.AnyFunSpec

class ExprHumanizerSpec extends AnyFunSpec {

  describe("humanize") {
    it("should humanize simple numbers") {
      val expr = Number(2)
      val humanReadable = ExprHumanizer.humanize(expr)
      assert(humanReadable == "2")
    }
    it("should humanize sum") {
      val expr = Sum(Number(2), Number(3))
      val humanReadable = ExprHumanizer.humanize(expr)
      assert(humanReadable == "2 + 3")
    }
    it("should humanize prod") {
      val expr = Prod(Number(1), Number(2))
      val humanReadable = ExprHumanizer.humanize(expr)
      assert(humanReadable == "1 * 2")
    }
    it("should humanize combination of prod and sum with parenthesis") {
      val expr = Prod(Sum(Number(2), Number(1)), Number(3))
      val humanReadable = ExprHumanizer.humanize(expr)
      assert(humanReadable == "(2 + 1) * 3")
    }
    it("should humanize combination of sum and prod with parenthesis") {
      val expr = Sum(Prod(Number(2), Number(1)), Number(3))
      val humanReadable = ExprHumanizer.humanize(expr)
      assert(humanReadable == "(2 * 1) + 3")
    }
    it("should humanize multiple nestings of sum without parenthesis") {
      val expr = Sum(Sum(Sum(Number(2), Number(1)), Number(3)), Number(5))
      val humanReadable = ExprHumanizer.humanize(expr)
      assert(humanReadable == "2 + 1 + 3 + 5")
    }
    it("should humanize multiple nestings of prod without parenthesis") {
      val expr = Prod(Prod(Prod(Number(2), Number(1)), Number(3)), Number(5))
      val humanReadable = ExprHumanizer.humanize(expr)
      assert(humanReadable == "2 * 1 * 3 * 5")
    }

    it("should humanize prod with nested sums") {
      val expr = Prod(Sum(Number(2), Number(1)), Sum(Number(3), Number(5)))
      val humanReadable = ExprHumanizer.humanize(expr)
      assert(humanReadable == "(2 + 1) * (3 + 5)")
    }

    it("should humanize sum with nested prods") {
      val expr = Sum(Prod(Number(2), Number(1)), Prod(Number(3), Number(5)))
      val humanReadable = ExprHumanizer.humanize(expr)
      assert(humanReadable == "(2 * 1) + (3 * 5)")
    }
  }

}
