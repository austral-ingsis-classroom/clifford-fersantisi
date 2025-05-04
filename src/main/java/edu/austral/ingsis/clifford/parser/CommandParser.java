package edu.austral.ingsis.clifford.parser;

import edu.austral.ingsis.clifford.command.*;
import java.util.Arrays;

public final class CommandParser {

  public ParsedCommand parse(String line) {
    String[] parts = line.trim().split("\\s+");
    String name = parts[0];
    String[] args = Arrays.copyOfRange(parts, 1, parts.length);

    switch (name) {
      case "mkdir" -> {
        return new ParsedCommand(new Mkdir(), args);
      }
      case "touch" -> {
        return new ParsedCommand(new Touch(), args);
      }
      case "cd" -> {
        return new ParsedCommand(new Cd(), args);
      }
      case "ls" -> {
        return new ParsedCommand(new Ls(), args);
      }
      case "pwd" -> {
        return new ParsedCommand(new Pwd(), args);
      }
      case "rm" -> {
        return new ParsedCommand(new Rm(), args);
      }
      default -> {
        return new ParsedCommand(new Unknown(name), args);
      }
    }
  }
}
