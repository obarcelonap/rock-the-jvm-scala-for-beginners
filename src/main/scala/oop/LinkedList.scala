package oop

import scala.annotation.tailrec

abstract class LinkedList[+T] {
  def head: T
  def tail: LinkedList[T]
  def isEmpty: Boolean
  def add[S >: T](node: S): LinkedList[S]

  def nodesAsString: String;
  override def toString: String = s"[${nodesAsString}]"

  def filter(predicate: Predicate[T]): LinkedList[T]
  def map[S](transformer: Transformer[T, S]): LinkedList[S]
  def flatMap[S](transformer: Transformer[T, LinkedList[S]]): LinkedList[S]
  def ++[S >: T](list: LinkedList[S]): LinkedList[S]
  def reverse: LinkedList[T]
}

object LinkedList {
  def create[T](nodes: T*): LinkedList[T] = {
    @tailrec
    def createRec[S](nodes: Seq[S], list: LinkedList[S] = EmptyLinkedList): LinkedList[S] =
      if (nodes.isEmpty) list
      else createRec(nodes.slice(0, nodes.length - 1), list.add(nodes.last))

    createRec(nodes)
  }
}

object EmptyLinkedList extends LinkedList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: LinkedList[Nothing] = EmptyLinkedList
  def isEmpty: Boolean = true
  def add[S >: Nothing](node: S): LinkedList[S] = new LinkedListNode(node, EmptyLinkedList)
  def nodesAsString: String = ""
  def filter(predicate: Predicate[Nothing]): LinkedList[Nothing] = EmptyLinkedList
  def map[S](transformer: Transformer[Nothing, S]): LinkedList[S] = EmptyLinkedList
  def flatMap[S](transformer: Transformer[Nothing, LinkedList[S]]): LinkedList[S] = EmptyLinkedList
  def ++[S >: Nothing](list: LinkedList[S]): LinkedList[S] = list
  def reverse: LinkedList[Nothing] = this
}

class LinkedListNode[T](node: T, nextNodes: LinkedList[T]) extends LinkedList[T] {
  def head: T = node
  def tail: LinkedList[T] = nextNodes
  def isEmpty: Boolean = false
  def add[S >: T](node: S): LinkedList[S] = new LinkedListNode(node, this)

  def nodesAsString: String = {
    @tailrec
    def asStringRec(accum: String, nextNodes: LinkedList[T]): String =
      if (nextNodes.isEmpty) accum
      else asStringRec(s"$accum, ${nextNodes.head}", nextNodes.tail)

    asStringRec(s"$node", nextNodes)
  }

  def reverse: LinkedList[T] = {
    @tailrec
    def reverseRec(list: LinkedList[T], accum: LinkedList[T] = EmptyLinkedList): LinkedList[T] =
      if (list.isEmpty) accum
      else reverseRec(list.tail, accum.add(list.head))

    reverseRec(this)
  }

  def filter(predicate: Predicate[T]): LinkedList[T] = {
    @tailrec
    def filterRec(list: LinkedList[T], accum: LinkedList[T] = EmptyLinkedList): LinkedList[T] =
      if (list.isEmpty) accum.reverse
      else filterRec(
        list.tail,
        if (predicate.test(list.head)) accum.add(list.head)
        else accum
      )

    filterRec(this)
  }

  def map[S](transformer: Transformer[T, S]): LinkedList[S] = {
    @tailrec
    def mapRec(list: LinkedList[T], accum: LinkedList[S] = EmptyLinkedList): LinkedList[S] =
      if (list.isEmpty) accum.reverse
      else mapRec(list.tail, accum.add(transformer.transform(list.head)))

    mapRec(this)
  }

  def flatMap[S](transformer: Transformer[T, LinkedList[S]]): LinkedList[S] = {
    @tailrec
    def flatMapRec(list: LinkedList[T], accum: LinkedList[S] = EmptyLinkedList): LinkedList[S] =
      if (list.isEmpty) accum
      else flatMapRec(list.tail, accum ++ transformer.transform(list.head))

    flatMapRec(this)
  }

  def ++[S >: T](list: LinkedList[S]): LinkedList[S] = new LinkedListNode(node, nextNodes ++ list)

  override def equals(obj: Any): Boolean = {
    @tailrec
    def equalsRec(obj1: Any, obj2: Any, accum: Boolean = true): Boolean =
      if (!accum) false
      else if (!obj1.isInstanceOf[LinkedList[T]]) false
      else if (!obj2.isInstanceOf[LinkedList[T]]) false
      else {
        val list1 = obj1.asInstanceOf[LinkedList[T]]
        val list2 = obj2.asInstanceOf[LinkedList[T]]

        if (list1.isEmpty && list2.isEmpty) true
        else if (list1.isEmpty != list2.isEmpty) false
        else equalsRec(list1.tail, list2.tail, list1.head == list2.head)
      }

    equalsRec(this, obj)
  }
}

trait Predicate[-T] {
  def test(value: T): Boolean
}

trait Transformer[-A, B] {
  def transform(value: A): B
}
