package oop

class Writer(firstName: String, surName: String, val year: Int = 0) {
  def fullName(): String = s"$firstName $surName"

}
