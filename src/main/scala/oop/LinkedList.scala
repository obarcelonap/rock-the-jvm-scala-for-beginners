package oop

import java.util.NoSuchElementException

import scala.annotation.tailrec

abstract class LinkedList[+T] {
  def head: T
  def tail: LinkedList[T]
  def isEmpty: Boolean
  def add[S >: T](node: S): LinkedList[S]

  def nodesAsString: String;
  override def toString: String = s"[${nodesAsString}]"

  def filter(predicate: Predicate[T]): LinkedList[T]
  def filter(predicate: (T) => Boolean): LinkedList[T]
  def map[S](transformer: Transformer[T, S]): LinkedList[S]
  def map[S](transformer: (T) => S): LinkedList[S]
  def flatMap[S](transformer: Transformer[T, LinkedList[S]]): LinkedList[S]
  def flatMap[S](transformer: (T) => LinkedList[S]): LinkedList[S]
  def ++[S >: T](list: LinkedList[S]): LinkedList[S]
  def reverse: LinkedList[T]
  def forEach(consumer: T => Unit): Unit;
  def sort(comparator: (T, T) => Int): LinkedList[T]
  def zipWith[S >: T](list: LinkedList[S], combiner: (S, S) => S): LinkedList[S]
  def fold[S >: T](initial: S)(accumulator:(S, S) => S): S
}

object LinkedList {
  def apply[T](nodes: T*): LinkedList[T] = {
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
  def filter(predicate: Nothing => Boolean): LinkedList[Nothing] = EmptyLinkedList
  def map[S](transformer: Transformer[Nothing, S]): LinkedList[S] = EmptyLinkedList
  def map[S](transformer: Nothing => S): LinkedList[S] = EmptyLinkedList
  def flatMap[S](transformer: Transformer[Nothing, LinkedList[S]]): LinkedList[S] = EmptyLinkedList
  def flatMap[S](transformer: Nothing => LinkedList[S]): LinkedList[S] = EmptyLinkedList
  def ++[S >: Nothing](list: LinkedList[S]): LinkedList[S] = list
  def reverse: LinkedList[Nothing] = this
  def forEach(consumer: Nothing => Unit): Unit = ()
  def sort(comparator: (Nothing, Nothing) => Int): LinkedList[Nothing] = EmptyLinkedList
  def zipWith[S >: Nothing](list: LinkedList[S], combiner: (S, S) => S): LinkedList[S] = throw new NoSuchElementException
  def fold[S >: Nothing](initial: S)(accumulator:(S, S) => S): S = initial
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
  def filter(predicate: (T) => Boolean): LinkedList[T] =
    if (predicate(node)) new LinkedListNode(node, nextNodes.filter(predicate))
    else nextNodes.filter(predicate)

  def map[S](transformer: Transformer[T, S]): LinkedList[S] = {
    @tailrec
    def mapRec(list: LinkedList[T], accum: LinkedList[S] = EmptyLinkedList): LinkedList[S] =
      if (list.isEmpty) accum.reverse
      else mapRec(list.tail, accum.add(transformer.transform(list.head)))

    mapRec(this)
  }
  def map[S](transformer: (T) => S): LinkedList[S] =
    new LinkedListNode(transformer(node), nextNodes.map(transformer))

  def flatMap[S](transformer: Transformer[T, LinkedList[S]]): LinkedList[S] = {
    @tailrec
    def flatMapRec(list: LinkedList[T], accum: LinkedList[S] = EmptyLinkedList): LinkedList[S] =
      if (list.isEmpty) accum
      else flatMapRec(list.tail, accum ++ transformer.transform(list.head))

    flatMapRec(this)
  }
  def flatMap[S](transformer: (T) => LinkedList[S]): LinkedList[S] =
    transformer(node) ++ nextNodes.flatMap(transformer)

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

  def forEach(consumer: T => Unit): Unit = {
    consumer(node)
    nextNodes.forEach(consumer)
  }

  def sort(comparator: (T, T) => Int): LinkedList[T] = {
    @tailrec
    def bubble(list: LinkedList[T], accum: LinkedList[T] = EmptyLinkedList): LinkedList[T] =
      if (list.isEmpty) accum
      else if (list.tail.isEmpty) accum ++ LinkedList(list.head)
      else if(comparator(list.head, list.tail.head) > 0)
        bubble(list.tail.tail.add(list.head),  accum ++ LinkedList(list.tail.head))
      else bubble(list.tail, accum ++ LinkedList(list.head))

    @tailrec
    def bubbleSort(list: LinkedList[T], prev: LinkedList[T] = EmptyLinkedList): LinkedList[T] =
      if (list.equals(prev)) list
      else bubbleSort(bubble(list), list)

    bubbleSort(this)
  }
  def zipWith[S >: T](list: LinkedList[S], combiner: (S, S) => S): LinkedList[S] = {
    @tailrec
    def zipWithRec(list1: LinkedList[S], list2: LinkedList[S], accum: LinkedList[S]): LinkedList[S] =
      if (list1.isEmpty != list2.isEmpty) throw new NoSuchElementException
      else if (list1.isEmpty && list2.isEmpty) accum.reverse
      else zipWithRec(list1.tail, list2.tail, accum.add(combiner(list1.head, list2.head)))

    if (list.isEmpty) throw new NoSuchElementException
    else zipWithRec(this, list, EmptyLinkedList)
  }

  def fold[S >: T](initial: S)(accumulator:(S, S) => S): S = {
    @tailrec
    def foldRec(list: LinkedList[S], accum: S): S =
      if (list.isEmpty) accum
      else foldRec(list.tail, accumulator(list.head, accum))

    foldRec(this, initial)
  }
}

trait Predicate[-T] {
  def test(value: T): Boolean
}

trait Transformer[-A, B] {
  def transform(value: A): B
}
