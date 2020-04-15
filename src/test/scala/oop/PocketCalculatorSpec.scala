package oop

import oop.PocketCalculator.{DivisionByZeroException, OverflowException, UnderflowException}
import org.scalatest.funspec.AnyFunSpec

class PocketCalculatorSpec extends AnyFunSpec {
  describe("add") {
    it("should add two numbers") {
      val result = PocketCalculator.add(1, 3)

      assert(result == 4)
    }
    it("should throw OverflowException when bigger than max Int") {
      intercept[OverflowException] {
        PocketCalculator.add(Integer.MAX_VALUE, 1)
      }
    }
    it("should throw UnderflowException when smaller than min Int") {
      intercept[UnderflowException] {
        PocketCalculator.add(Integer.MIN_VALUE, -1)
      }
    }
  }
  describe("subtract") {
    it("should subtract two numbers") {
      val result = PocketCalculator.subtract(3, 1)

      assert(result == 2)
    }
    it("should throw OverflowException when bigger than max Int") {
      intercept[OverflowException] {
        PocketCalculator.subtract(Integer.MAX_VALUE, -1)
      }
    }
    it("should throw UnderflowException when smaller than min Int") {
      intercept[UnderflowException] {
        PocketCalculator.subtract(Integer.MIN_VALUE, 1)
      }
    }
  }
  describe("multiply") {
    it("should multiply two numbers") {
      val result = PocketCalculator.multiply(3, 3)

      assert(result == 9)
    }
    it("should throw OverflowException when result is bigger than max Int with positive numbers") {
      intercept[OverflowException] {
        PocketCalculator.multiply(Integer.MAX_VALUE, 2)
      }
    }
    it("should throw OverflowException when result is bigger than max Int with negative numbers") {
      intercept[OverflowException] {
        PocketCalculator.multiply(Integer.MIN_VALUE, -5)
      }
    }
//    it("should throw UnderflowException when result is smaller than min Int with first number being negative") {
    ////      intercept[UnderflowException] {
    ////        PocketCalculator.multiply(Integer.MIN_VALUE, 3)
    ////      }
    ////    }
    it("should throw UnderflowException when result is smaller than min Int with second number being negative") {
      intercept[UnderflowException] {
        PocketCalculator.multiply(Integer.MAX_VALUE, -2)
      }
    }
  }
  describe("divide") {
    it("should divide two numbers") {
      val result = PocketCalculator.divide(8, 2)

      assert(result == 4)
    }
    it("should throw DivisionByZeroException when second value is 0") {
      intercept[DivisionByZeroException] {
        PocketCalculator.divide(1, 0)
      }
    }
  }
}
