package fp

import fp.ExprHumanizer.humanize

trait Expr
case class Number(n: Int) extends Expr
case class Sum(e1: Expr, e2: Expr) extends Expr
case class Prod(e1: Expr, e2: Expr) extends Expr

object ExprHumanizer {

  def humanize(expr: Expr): String = {
    expr match {
      case Number(n) => s"$n"
      case Sum(e1, e2) => s"${humanize(e1, expr)} + ${humanize(e2, expr)}"
      case Prod(e1, e2) => s"${humanize(e1, expr)} * ${humanize(e2, expr)}"
    }
  }

  private def humanize(expr: Expr, prev: Expr): String =
    expr match {
      case Sum(_, _) =>
        prev match {
          case Sum(_, _) => humanize(expr)
          case _ => s"(${humanize(expr)})"
        }
      case Prod(_, _) =>
        prev match {
          case Prod(_, _) => humanize(expr)
          case _ => s"(${humanize(expr)})"
        }
      case _ => humanize(expr)
    }
}