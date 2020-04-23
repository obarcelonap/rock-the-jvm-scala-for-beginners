package filesystem

import filesystem.CommandLine.NewCommand
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class FilesystemSpec extends AnyFunSpec with Matchers {

  describe("boot") {
    it("exits with 0 when command is exit") {
      val commandLine = CommandLine(() => NewCommand("exit"), (s: String) => {})

      val statusCode = Filesystem.boot(commandLine, State())

      statusCode should be(0)
    }
  }

}
