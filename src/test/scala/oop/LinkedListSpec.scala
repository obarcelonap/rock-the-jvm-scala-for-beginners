package oop

import org.scalatest.funspec.AnyFunSpec

class LinkedListSpec extends AnyFunSpec {

  describe("create") {
    it("should create empty if no args") {
      assert(LinkedList.create() == EmptyLinkedList)
    }
    it("should create with nodes in specified order") {
      val listA = new LinkedListNode(1, new LinkedListNode(2, new LinkedListNode(3, EmptyLinkedList)))
      val listB = LinkedList.create(1, 2, 3)

      assert(listA == listB)
    }
  }

  describe("head") {
    it("should throw NoSuchElementException when empty") {
      val list = EmptyLinkedList

      intercept[NoSuchElementException] {
        list.head
      }
    }
    it("should return first element") {
      val list = LinkedList.create(1)

      assert(list.head == 1)
    }
  }

  describe("tail") {
    it("should return empty when empty") {
      val list = EmptyLinkedList

      assert(list.tail == EmptyLinkedList)
    }
    it("should return the next elements") {
      val list = LinkedList.create(1, 2)

      assert(list.tail == LinkedList.create(2))
    }
  }

  describe("isEmpty") {
    it("should be true when empty") {
      val list = EmptyLinkedList

      assert(list.isEmpty)
    }
    it("should be false when there are elements") {
      val list = LinkedList.create(1)

      assert(!list.isEmpty)
    }
  }

  describe("add") {
    it("should add a new value") {
      val list = EmptyLinkedList.add(2)

      assert(list.head == 2)
    }
    it("should be added to a new node when there are elements") {
      val list = LinkedList.create(1)
        .add(2)

      assert(list.head == 2)
    }
  }

  describe("toString") {
    it("should format as square brackets when empty") {
      val listAsString = EmptyLinkedList.toString

      assert(listAsString == "[]")
    }
    it("should format elements surrounded by square brackets") {
      val listAsString = new LinkedListNode(1, new LinkedListNode(2, EmptyLinkedList)).toString

      assert(listAsString == "[1, 2]")
    }
  }

  describe("filter") {
    val evenNumber = new Predicate[Int] {
      def test(value: Int): Boolean = value % 2 == 0
    }

    it("should return empty list") {
      val list = EmptyLinkedList.filter(evenNumber)

      assert(list == EmptyLinkedList)
    }
    it("should filter only for even numbers") {
      val list = LinkedList.create(1, 2, 3, 4)

      val filteredList = list.filter(evenNumber)

      assert(filteredList == LinkedList.create(2, 4))
    }
  }

  describe("map") {
    val doubleNumber = new Transformer[Int, Int] {
      def transform(value: Int): Int = value * 2
    };

    it("should return empty list") {
      val list = EmptyLinkedList.map(doubleNumber)

      assert(list == EmptyLinkedList)
    }
    it("should transform the numbers to its double") {
      val list = LinkedList.create(1, 2, 3, 4)

      val transformedList = list.map(doubleNumber)

      assert(transformedList == LinkedList.create(2, 4, 6, 8))
    }
  }

  describe("flatMap") {
    val plusOne = new Transformer[Int, LinkedList[Int]] {
      override def transform(value: Int): LinkedList[Int] = new LinkedListNode(value, new LinkedListNode(value + 1, EmptyLinkedList))
    }

    it("should return empty list") {
      val list = EmptyLinkedList.flatMap(plusOne)

      assert(list == EmptyLinkedList)
    }
    it("should transform the numbers to itself and itself plus one") {
      val list = LinkedList.create(1, 2)

      val transformedList = list.flatMap(plusOne)

      assert(transformedList == LinkedList.create(1, 2, 2, 3))
    }
  }

  describe("++") {
    it("should return first when second is empty") {
      val listA = LinkedList.create(1)
      val listB = EmptyLinkedList

      val concatenated = listA ++ listB

      assert(concatenated == listA)
    }
    it("should return second when first is empty") {
      val listA = EmptyLinkedList
      val listB = LinkedList.create(1)

      val concatenated = listA ++ listB

      assert(concatenated == listB)
    }
    it("should return concatenated list when both have values") {
      val listA = LinkedList.create(1, 2)
      val listB = LinkedList.create(1, 3)

      val concatenated = listA ++ listB

      assert(concatenated == LinkedList.create(1, 2, 1, 3))
    }
  }
}

