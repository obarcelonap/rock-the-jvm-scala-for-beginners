package fp

object Functions {

  val concat: (String, String) => String = (v1, v2) => v1 + v2
  val adder: (Int) => (Int) => Int = (v1) => (v2) => v1 + v2

  def toCurry[A, B, C](f: (A, B) => C): (A => B => C) =
    x => y => f(x, y)
  def fromCurry[A, B, C](f: A => B => C): (A, B) => C =
    (x, y) => f(x)(y)
  def compose[A, B, C](f: A => B, g: C => A): C => B =
    x => f(g(x))
  def andThen[A, B, C](f: A => B, g: B => C): A => C =
    x => g(f(x))
}
