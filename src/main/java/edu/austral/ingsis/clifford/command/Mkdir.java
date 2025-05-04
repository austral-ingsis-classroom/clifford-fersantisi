package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.FileSystemManager;

public final class Mkdir implements Command {
  @Override
  public String execute(FileSystemManager fsm, String[] args) {
    if (args.length != 1) {
      return "Error: mkdir expects exactly one argument.";
    }
    return fsm.mkdir(args[0]);
  }
}
