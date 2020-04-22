package oop

import org.scalatest.funspec.AnyFunSpec

class LinkedListSpec extends AnyFunSpec  {

  describe("create") {
    it("should create empty if no args") {
      assert(LinkedList() == EmptyLinkedList)
    }
    it("should create with nodes in specified order") {
      val listA = new LinkedListNode(1, new LinkedListNode(2, new LinkedListNode(3, EmptyLinkedList)))
      val listB = LinkedList(1, 2, 3)

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
      val list = LinkedList(1)

      assert(list.head == 1)
    }
  }

  describe("tail") {
    it("should return empty when empty") {
      val list = EmptyLinkedList

      assert(list.tail == EmptyLinkedList)
    }
    it("should return the next elements") {
      val list = LinkedList(1, 2)

      assert(list.tail == LinkedList(2))
    }
  }

  describe("isEmpty") {
    it("should be true when empty") {
      val list = EmptyLinkedList

      assert(list.isEmpty)
    }
    it("should be false when there are elements") {
      val list = LinkedList(1)

      assert(!list.isEmpty)
    }
  }

  describe("add") {
    it("should add a new value") {
      val list = EmptyLinkedList.add(2)

      assert(list.head == 2)
    }
    it("should be added to a new node when there are elements") {
      val list = LinkedList(1)
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
    val evenNumberPredicate = new Predicate[Int] {
      def test(value: Int): Boolean = value % 2 == 0
    }

    describe("when Predicate") {
      it("should return empty list") {
        val list = EmptyLinkedList.filter(evenNumberPredicate)

        assert(list == EmptyLinkedList)
      }
      it("should filter only for even numbers") {
        val list = LinkedList(1, 2, 3, 4)

        val filteredList = list.filter(evenNumberPredicate)

        assert(filteredList == LinkedList(2, 4))
      }
    }
    describe("when function") {
      val evenNumber: (Int) => Boolean = evenNumberPredicate.test
      it("should return empty list") {
        val list = EmptyLinkedList.filter(evenNumber)

        assert(list == EmptyLinkedList)
      }
      it("should filter only for even numbers") {
        val list = LinkedList(1, 2, 3, 4)

        val filteredList = list.filter(evenNumber)

        assert(filteredList == LinkedList(2, 4))
      }
    }
  }

  describe("map") {
    val doublerTransformer = new Transformer[Int, Int] {
      def transform(value: Int): Int = value * 2
    };

    describe("when Transformer") {
      it("should return empty list") {
        val list = EmptyLinkedList.map(doublerTransformer)

        assert(list == EmptyLinkedList)
      }
      it("should transform the numbers to its double") {
        val list = LinkedList(1, 2, 3, 4)

        val transformedList = list.map(doublerTransformer)

        assert(transformedList == LinkedList(2, 4, 6, 8))
      }
    }
    describe("when function") {
      val doubler: (Int) => Int = doublerTransformer.transform
      it("should return empty list") {
        val list = EmptyLinkedList.map(doubler)

        assert(list == EmptyLinkedList)
      }
      it("should transform the numbers to its double") {
        val list = LinkedList(1, 2, 3, 4)

        val transformedList = list.map(doubler)

        assert(transformedList == LinkedList(2, 4, 6, 8))
      }
    }
  }

  describe("flatMap") {
    val plusOneTransformer = new Transformer[Int, LinkedList[Int]] {
      override def transform(value: Int): LinkedList[Int] = LinkedList(value, value + 1)
    }

    describe("when Transformer") {
      it("should return empty list") {
        val list = EmptyLinkedList.flatMap(plusOneTransformer)

        assert(list == EmptyLinkedList)
      }
      it("should transform the numbers to itself and itself plus one") {
        val list = LinkedList(1, 2)

        val transformedList = list.flatMap(plusOneTransformer)

        assert(transformedList == LinkedList(1, 2, 2, 3))
      }
    }
    describe("when function") {
      val plusOne: Int => LinkedList[Int] = plusOneTransformer.transform

      it("should return empty list") {
        val list = EmptyLinkedList.flatMap(plusOne)

        assert(list == EmptyLinkedList)
      }
      it("should transform the numbers to itself and itself plus one") {
        val list = LinkedList(1, 2)

        val transformedList = list.flatMap(plusOne)

        assert(transformedList == LinkedList(1, 2, 2, 3))
      }
    }
  }

  describe("++") {
    it("should return first when second is empty") {
      val listA = LinkedList(1)
      val listB = EmptyLinkedList

      val concatenated = listA ++ listB

      assert(concatenated == listA)
    }
    it("should return second when first is empty") {
      val listA = EmptyLinkedList
      val listB = LinkedList(1)

      val concatenated = listA ++ listB

      assert(concatenated == listB)
    }
    it("should return concatenated list when both have values") {
      val listA = LinkedList(1, 2)
      val listB = LinkedList(1, 3)

      val concatenated = listA ++ listB

      assert(concatenated == LinkedList(1, 2, 1, 3))
    }
  }
  describe("forEach") {
    it ("should not do nothing when empty") {
      var totalSum = 0;
      val list = EmptyLinkedList

      list.forEach((node: Int) => totalSum += node)

      assert(totalSum == 0)
    }
    it("should call with the specified function all the list elements") {
      var totalSum = 0;
      val list = LinkedList(1, 2, 3)

      list.forEach((node) => totalSum += node)

      assert(totalSum == 6)
    }
  }

  describe("sort") {
    it ("should return empty when empty") {
      val list = EmptyLinkedList

      val sortedList = list.sort((v1: Int, v2: Int) => v2 - v1)

      assert(sortedList == EmptyLinkedList)
    }
    it("should sort ascendant") {
      val list = LinkedList(4, 1, 3, 2)

      val sortedList = list.sort((v1, v2) => v1 - v2)

      assert(sortedList == LinkedList(1, 2, 3, 4))
    }
    it("should sort descendant") {
      val list = LinkedList(4, 1, 3, 2)

      val sortedList = list.sort((v1, v2) => v2 - v1)

      assert(sortedList == LinkedList(4, 3, 2, 1))
    }
  }

  describe("zipWith") {
    it ("should fail with NoSuchElementException when empty list") {
      intercept[NoSuchElementException] {
        EmptyLinkedList.zipWith(LinkedList(1), (a: Int, b: Int) => a)
      }
    }
    it ("should fail with NoSuchElementException when zipping with empty list") {
      intercept[NoSuchElementException] {
        LinkedList(1).zipWith(EmptyLinkedList, _ + _)
      }
    }
    it ("should fail with NoSuchElementException when list has different sizes") {
      intercept[NoSuchElementException] {
        val list1 = LinkedList(1)
        val list2 = LinkedList(1, 2)
        list1.zipWith(list2, _ + _)
      }
    }
    it ("should zip adding both numbers") {
      val list1 = LinkedList(1, 2, 3)
      val list2 = LinkedList(4, 5, 6)

      val result = list1.zipWith(list2, _ + _)

      assert(result == LinkedList(5, 7, 9))
    }
  }
  describe("fold") {
    it ("should not do nothing when empty") {
      val list = EmptyLinkedList

      val res = list.fold(0)(_ + _)

      assert(res == 0)
    }
    it("should fold with the specified function all the list elements") {
      val list = LinkedList(1, 2, 3)

      val res = list.fold(0)(_ + _)

      assert(res == 6)
    }
  }

}

