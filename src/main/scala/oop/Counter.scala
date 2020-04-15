package oop

class Counter(value: Int) {
  def current(): Int = value
  def increment(amount: Int = 1) = new Counter(value + amount)
  def decrement(amount: Int = 1) = new Counter(value - amount)
}
