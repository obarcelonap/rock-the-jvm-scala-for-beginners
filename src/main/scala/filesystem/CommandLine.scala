package filesystem

import java.io.{InputStream, OutputStream}
import java.util.Scanner

import filesystem.CommandLine.NewCommand

object CommandLine {

  case class NewCommand(command: String, args: List[String] = List())

  def reader(inputStream: InputStream): () => NewCommand = {
    val scanner = new Scanner(inputStream)
    () => {
      val command = scanner.next

      val args = scanner.nextLine()
        .trim
        .split(" ")
        .map(_.trim)
        .filter(_.nonEmpty)
        .toList

      NewCommand(command, args)
    }
  }

  def writer(outputStream: OutputStream): String => Unit =
    value => outputStream.write(value.getBytes)

  def apply(inputStream: InputStream, outputStream: OutputStream): CommandLine =
    new CommandLine(reader(inputStream), writer(outputStream))
}

case class CommandLine(read: () => NewCommand, write: String => Unit)
