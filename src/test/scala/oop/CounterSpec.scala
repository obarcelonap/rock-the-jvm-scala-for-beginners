package oop

import org.scalatest.funspec.AnyFunSpec

class CounterSpec extends AnyFunSpec {

  it("returns the current") {
    val counter = new Counter(3)

    assert(counter.current() == 3)
  }

  describe("increment") {
    it("increments by 1 by default") {
      val counter = new Counter(2)

      assert(counter.increment().current() == 3)
    }
    it("increments by amount") {
      val counter = new Counter(2)

      assert(counter.increment(2).current() == 4)
    }
  }

  describe("decrement") {
    it("decrements by 1 by default") {
      val counter = new Counter(2)

      assert(counter.decrement().current() == 1)
    }
    it("decrements by amount") {
      val counter = new Counter(2)

      assert(counter.decrement(2).current() == 0)
    }
  }
}
