package filesystem

import filesystem.CommandLine.NewCommand
import filesystem.commands.Command

import scala.annotation.tailrec

object Filesystem extends App {

  @tailrec
  def boot(commandLine: CommandLine, state: State): State = {
    if (!state.out.isEmpty) {
      commandLine.write(s"${state.out}\n")
    }

    if (!state.running) state
    else {
      commandLine.write("> ")
      val NewCommand(name, args) = commandLine.read()
      boot(commandLine, Command.get(name)(state, args))
    }
  }

  boot(CommandLine(System.in, System.out), State(out = "Welcome"))
}
