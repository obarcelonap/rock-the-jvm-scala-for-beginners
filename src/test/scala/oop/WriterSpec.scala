package oop

import org.scalatest.funspec.AnyFunSpec

class WriterSpec extends AnyFunSpec {

  describe("writter") {
    it("full name is a concatenation of first name and surname") {
      val writer = new Writer("Martin", "Fowler")
      val fullName = writer.fullName()
      assert(fullName == "Martin Fowler")
    }
  }

}
