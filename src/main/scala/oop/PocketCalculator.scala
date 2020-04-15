package oop

object PocketCalculator {

  def add(value1: Int, value2: Int): Int = {
    val res = value1 + value2

    if (value1 > 0 && value2 > 0 && res < 0) throw new OverflowException
    else if (value1 < 0 && value2 < 0 && res > 0) throw new UnderflowException
    else res
  }
  class OverflowException extends Exception

  def subtract(value1: Int, value2: Int): Int = {
    val res = value1 - value2

    if (value1 > 0 && value2 < 0 && res < 0) throw new OverflowException
    else if (value1 < 0 && value2 > 0 && res > 0) throw new UnderflowException
    else res
  }
  class UnderflowException extends Exception

  def multiply(value1: Int, value2: Int): Int = {
    val res = value1 * value2

    if (value1 > 0 && value2 > 0 && res < 0) throw new OverflowException
    else if (value1 < 0 && value2 < 0 && res < 0) throw new OverflowException
    else if (value1 < 0 && value2 > 0 && res > 0) throw new UnderflowException
    else if (value1 > 0 && value2 < 0 && res > 0) throw new UnderflowException
    else res

  }

  def divide(value1: Int, value2: Int): Int =
    if (value2 == 0) throw new DivisionByZeroException
    else value1 / value2

  class DivisionByZeroException extends Exception
}
