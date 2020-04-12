package basics

import org.scalatest.funspec.AnyFunSpec

class RecursionSpec extends AnyFunSpec {

  describe("concat") {
    it("does nothing when times is 1") {
      val actual = Recursion.concat("bla", 1)
      assert(actual == "bla")
    }
    it("concat string 5 times") {
      val actual = Recursion.concat("bla", 5)
      assert(actual == "blablablablabla")
    }
  }

  describe("isPrime") {
    Map(
      0 -> false,
      1 -> false,
      2 -> true,
      3 -> true,
      4 -> false,
      5 -> true,
      6 -> false,
      7 -> true,
      8 -> false,
      9 -> false,
      10 -> false,
    )
      .foreach(testCase =>
        it(s"${testCase._1} is${if (testCase._2) "" else " not"} prime") {
          assert(Recursion.isPrime(testCase._1) == testCase._2)
        }
      )
  }

  describe("fibonacci") {
    Map(
      0 -> 0,
      1 -> 1,
      2 -> 1,
      3 -> 2,
      4 -> 3,
      5 -> 5,
      6 -> 8,
      7 -> 13,
      8 -> 21,
      9 -> 34,
      10 -> 55,
    )
      .foreach(testCase =>
        it(s"fibonacci of ${testCase._1} is ${testCase._2}") {
          assert(Recursion.fibonacci(testCase._1) == testCase._2)
        }
      )
  }
}
