package basics

import scala.annotation.tailrec

object Recursion extends App {

  def concat(aString: String, times: Int): String = {
    @tailrec
    def concatRec(times: Int, accum: String): String =
      if (times == 1) accum
      else concatRec(times - 1, accum + aString)

    concatRec(times, aString)
  }

  def isPrime(value: Int): Boolean = {
    @tailrec
    def isPrimeRec(t: Int, isStillPrime: Boolean): Boolean =
      if (t <= 1) isStillPrime
      else isPrimeRec(t - 1, value % t != 0 && isStillPrime)

    if (value <= 1) false
    else isPrimeRec(value - 1, true)
  }

  def fibonacci(value: Int): Int = {
    @tailrec
    def fibonacciRec(i: Int, last: Int, nextToLast: Int): Int =
      if (i >= value) last
      else fibonacciRec(i + 1, last + nextToLast, last)

    if (value==0) 0
    else if (value <= 2) 1
    else fibonacciRec(2, 1, 1)
  }
}
