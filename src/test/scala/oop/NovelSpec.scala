package oop

import org.scalatest.funspec.AnyFunSpec

class NovelSpec extends AnyFunSpec {

  val robertMartin = new Writer("Robert", "Martin", 1952)
  val kentBeck = new Writer("Kent", "Beck", 1955)

  val cleanCode = new Novel("Clean code", 2008, robertMartin);

  it("authorAge is substracted from author's age and novel year") {
    val robertMartinAge = cleanCode.authorAge();
    assert(robertMartinAge == 56)
  }
  describe("isWrittenBy") {
    it("returns true when same author") {
      assert(cleanCode.isWrittenBy(robertMartin))
    }
    it("returns false when other author") {
      assert(!cleanCode.isWrittenBy(kentBeck))
    }
  }
  describe("copy") {
    val cleanCode2ndEdition = cleanCode.copy(2010)
    it("returns a copy of the object") {
      assert(cleanCode != cleanCode2ndEdition)
    }
  }

}
