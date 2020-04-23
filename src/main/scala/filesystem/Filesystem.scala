package filesystem

import filesystem.CommandLine.NewCommand
import filesystem.commands.{Exit, NotFound}

object Filesystem extends App {

  def boot(commandLine: CommandLine, initialState: State): Int = {
    var state = initialState
    while (state.running) {
      commandLine.write("> ")

      val NewCommand(command, args) = commandLine.read()
      state = state.copy(command = command, args = args)

      state = state.command match {
        case "exit" => Exit(state)
        case _ => NotFound(state)
      }

      commandLine.write(s"${state.out}\n")
    }

    0
  }

  boot(CommandLine(System.in, System.out), State())
}
