package edu.austral.ingsis.clifford.parser;

import edu.austral.ingsis.clifford.command.Command;

public class ParsedCommand {
  private final Command command;
  private final String[] args;

  public ParsedCommand(Command command, String[] args) {
    this.command = command;
    this.args = args.clone();
  }

  public Command getCommand() {
    return command;
  }

  public String[] getArgs() {
    return args.clone();
  }
}
