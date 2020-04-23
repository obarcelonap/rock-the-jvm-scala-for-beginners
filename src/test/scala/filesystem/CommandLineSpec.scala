package filesystem

import java.io.{ByteArrayInputStream, InputStream}

import filesystem.CommandLine.NewCommand
import org.scalatest.Inside
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers


class CommandLineSpec extends AnyFunSpec with Inside with Matchers {

  describe("reader") {
    it("reads single command from input stream") {
      val read = CommandLine.reader(stringInputStream("hello\n"))

      val cmd = read()

      inside(cmd) {
        case NewCommand(command, args) =>
          command should be("hello")
          args should be(empty)
      }
    }

    it("reads command with args from input stream") {
      val read = CommandLine.reader(stringInputStream("hello my friend\n"))

      val cmd = read()

      inside(cmd) {
        case NewCommand(command, args) =>
          command should be("hello")
          args should contain allOf("my", "friend")
      }
    }

    it("strips extra spaces before and after the whole command") {
      val read = CommandLine.reader(stringInputStream(" hello   my  friend  \n"))

      val cmd = read()

      inside(cmd) {
        case NewCommand(command, args) =>
          command should be("hello")
          args should contain allOf("my", "friend")
      }
    }
  }

  def stringInputStream(value: String): InputStream = new ByteArrayInputStream(value.getBytes("UTF-8"))
}
