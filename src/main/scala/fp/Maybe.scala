package fp

abstract class Maybe[+T] {
  def map[S](f: T => S): Maybe[S]
  def flatMap[S](value: T => Maybe[S]): Maybe[S]
  def filter(f: T => Boolean): Maybe[T]
}

case object Empty extends Maybe[Nothing] {
  override def map[S](f: Nothing => S): Maybe[S] = Empty
  override def flatMap[S](f: Nothing => Maybe[S]): Maybe[S] = Empty
  override def filter(function: Nothing => Boolean): Maybe[Nothing] = Empty
}

case class Value[T](v: T) extends Maybe[T] {
  override def map[S](f: T => S): Maybe[S] = Value(f(v))
  override def flatMap[S](f: T => Maybe[S]): Maybe[S] = f(v)
  override def filter(f: T => Boolean): Maybe[T] =
    if (f(v)) Value(v)
    else Empty
}
