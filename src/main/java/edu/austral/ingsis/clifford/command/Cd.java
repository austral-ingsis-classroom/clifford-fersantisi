package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.FileSystemManager;

public final class Cd implements Command {
  @Override
  public String execute(FileSystemManager fsm, String[] args) {
    if (args.length != 1) return "Error: cd expects exactly one argument.";
    return fsm.cd(args[0]);
  }
}
