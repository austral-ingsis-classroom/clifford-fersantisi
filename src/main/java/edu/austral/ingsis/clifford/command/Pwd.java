package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.FileSystemManager;

public final class Pwd implements Command {
  @Override
  public String execute(FileSystemManager fsm, String[] args) {
    if (args.length != 0) return "Error: pwd expects no arguments.";
    return fsm.pwd();
  }
}
