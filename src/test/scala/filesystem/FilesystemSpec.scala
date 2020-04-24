package filesystem

import filesystem.CommandLine.NewCommand
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class FilesystemSpec extends AnyFunSpec with Matchers {

  describe("boot") {
    it("exits when command is exit") {
      val commandLine = CommandLine(() => NewCommand("exit"), (s: String) => {})

      val state = Filesystem.boot(commandLine, State())

      state.running should be(false)
    }
  }

}
