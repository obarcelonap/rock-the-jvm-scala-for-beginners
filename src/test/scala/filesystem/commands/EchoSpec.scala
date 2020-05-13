package filesystem.commands

import filesystem.{Dir, File, RootDir, State}
import org.scalatest.Inside
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class EchoSpec extends AnyFunSpec with Inside with Matchers  {

  describe("apply") {
    it("outputs nothing when there is no message") {
      val state = State()

      val newState = Echo(state)

      newState.out should be (empty)
    }
    it("prints by console the message") {
      val state = State()

      val newState = Echo(state, List("hello", "world"))

      newState.out should be ("hello world")
    }
    it("prints into new file the message when using '>' operator") {
      val state = State()

      val newState = Echo(state, List("hello", "world", ">", "helloworld.txt"))

      inside (newState.root) {
        case Dir(_,_, List(File(name, path, content))) =>
          name should be ("helloworld.txt")
          path should be ("/")
          content should be ("hello world")
      }
    }
    it("replaces the content into an existing file when using '>' operator") {
      val rootDir = RootDir.copy(entries = List(
        File("helloworld.txt", "/", "ciaociao"),
      ))
      val state = State(root = rootDir)

      val newState = Echo(state, List("hello", "world", ">", "helloworld.txt"))

      inside (newState.root) {
        case Dir(_,_, List(File(name, path, content))) =>
          name should be ("helloworld.txt")
          path should be ("/")
          content should be ("hello world")
      }
    }
    it("prints into new file the message when using '>>' operator") {
      val state = State()

      val newState = Echo(state, List("hello", "world", ">>", "helloworld.txt"))

      inside (newState.root) {
        case Dir(_,_, List(File(name, path, content))) =>
          name should be ("helloworld.txt")
          path should be ("/")
          content should be ("hello world")
      }
    }
    it("appends the content into an existing file when using '>>' operator") {
      val rootDir = RootDir.copy(entries = List(
        File("helloworld.txt", "/", "ciaociao"),
      ))
      val state = State(root = rootDir)

      val newState = Echo(state, List("hello", "world", ">>", "helloworld.txt"))

      inside (newState.root) {
        case Dir(_,_, List(File(name, path, content))) =>
          name should be ("helloworld.txt")
          path should be ("/")
          content should be ("ciaociao hello world")
      }
    }
  }
}
