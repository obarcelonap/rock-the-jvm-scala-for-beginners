package fp

import org.scalatest.funspec.AnyFunSpec

class MaybeSpec extends AnyFunSpec {

  describe("map") {
    it("should return empty when empty") {
      val m = Empty.map((v: Int) => v)

      assert(m == Empty)
    }
    it("should apply the mapping function") {
      val m = Value(1).map(_ + "hello")

      assert(m == Value("1hello"))
    }
  }
  describe("flatMap") {
    it("should return empty when empty") {
      val m = Empty.flatMap((v: Int) => Value(v))

      assert(m == Empty)
    }
    it("should apply the mapping function flat mapping the result") {
      val m = Value(1).flatMap(v => Value(v + "hello"))

      assert(m == Value("1hello"))
    }
  }
  describe("filter") {
    it("should return empty when empty") {
      val m = Empty.filter(_ => true)

      assert(m == Empty)
    }
    it("should return empty when value does not match specified function") {
      val m = Value(1).filter(_ == 0)

      assert(m == Empty)
    }
    it("should return itself when value does match specified function") {
      val m = Value(1).filter(_ == 1)

      assert(m == Value(1))
    }
  }
}
