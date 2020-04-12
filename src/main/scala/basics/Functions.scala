package basics

object Functions extends App {

  // 1.
  def greeting(name:String, age:Int): Unit = println("Hi my name is " + name + " and I am " + age + " years old.")
  greeting("Oriol", 34)

  // 2.
  def factorial(n: Int): Int =
    if (n > 1) factorial(n-1) * n
    else n;
  println(factorial(3))

  // 3.
  def fibonacci(n: Int): Int =
    if (n<=2) 1
    else fibonacci(n - 1) + fibonacci(n - 2)
  println(fibonacci(8))

  // 4.
  def prime(n: Int): Boolean = {
    def isPrime(t: Int): Boolean =
      if (t <= 1) true
      else n % t != 0 && isPrime(t - 1)

    isPrime(n / 2)
  }
  println(prime(37))
  println(prime(2003))
  println(prime(37*17))
}
