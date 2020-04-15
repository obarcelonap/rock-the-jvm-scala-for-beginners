package oop

class Novel(name: String, year: Int, author: Writer) {
  def copy(newReleaseYear: Int) = new Novel(this.name, year, this.author)

  def authorAge(): Int = year - author.year
  def isWrittenBy(author: Writer): Boolean = this.author.equals(author)
}
