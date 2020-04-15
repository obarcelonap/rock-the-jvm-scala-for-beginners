package oop

import org.scalatest.funspec.AnyFunSpec

class IntLinkedListSpec extends AnyFunSpec {

  describe("head") {
    it("should throw NoSuchElementException when empty") {
      val list = EmptyIntLinkedList

      intercept[NoSuchElementException] {
        list.head
      }
    }
    it("should return first element") {
      val list = new IntLinkedListNode(1, EmptyIntLinkedList)

      assert(list.head == 1)
    }
  }

  describe("tail") {
    it("should throw NoSuchElementException when empty") {
      val list = EmptyIntLinkedList

      intercept[NoSuchElementException] {
        list.tail
      }
    }
    it("should return the next elements") {
      val list = new IntLinkedListNode(1, EmptyIntLinkedList)

      assert(list.tail == EmptyIntLinkedList)
    }
  }

  describe("isEmpty") {
    it("should be true when empty") {
      val list = EmptyIntLinkedList

      assert(list.isEmpty)
    }
    it("should be false when there are elements") {
      val list = new IntLinkedListNode(1, EmptyIntLinkedList)

      assert(!list.isEmpty)
    }
  }

  describe("add") {
    it("should add a new value") {
      val list = EmptyIntLinkedList.add(2)

      assert(list.head == 2)
    }
    it("should be added to a new node when there are elements") {
      val list = new IntLinkedListNode(1, EmptyIntLinkedList)
          .add(2)

      assert(list.head == 2)
    }
  }

  describe("toString") {
    it("should format as square brackets when empty") {
      val listAsString = EmptyIntLinkedList.toString

      assert(listAsString == "[]")
    }
    it("should format elements surrounded by square brackets") {
      val listAsString = new IntLinkedListNode(1, new IntLinkedListNode(2, EmptyIntLinkedList)).toString

      assert(listAsString == "[1, 2]")
    }
  }
}
