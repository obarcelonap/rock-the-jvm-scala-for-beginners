package oop

import java.util.NoSuchElementException

abstract class IntLinkedList {
  def head: Int
  def tail: IntLinkedList
  def isEmpty: Boolean
  def add(element: Int): IntLinkedList

  def elementsAsString: String;
  override def toString: String = s"[${elementsAsString}]"
}

object EmptyIntLinkedList extends IntLinkedList {
  def head: Int = throw new NoSuchElementException
  def tail: IntLinkedList = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(element: Int): IntLinkedList = new IntLinkedListNode(element, EmptyIntLinkedList)
  def elementsAsString: String = ""
}

class IntLinkedListNode(element: Int, nextElements: IntLinkedList) extends IntLinkedList {
  def head: Int = element
  def tail: IntLinkedList = nextElements
  def isEmpty: Boolean = false
  def add(element: Int): IntLinkedList = new IntLinkedListNode(element, this)
  def elementsAsString: String =
    if (nextElements.isEmpty) s"$element"
    else s"$element, ${nextElements.elementsAsString}"
}
