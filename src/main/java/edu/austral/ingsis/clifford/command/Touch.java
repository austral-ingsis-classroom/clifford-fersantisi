package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.FileSystemManager;

public final class Touch implements Command {
  @Override
  public String execute(FileSystemManager fsm, String[] args) {
    if (args.length != 1) return "Error: touch expects exactly one argument.";
    return fsm.touch(args[0]);
  }
}
