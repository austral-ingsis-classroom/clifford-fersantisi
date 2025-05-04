package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.FileSystemManager;

public final class Ls implements Command {
  @Override
  public String execute(FileSystemManager fsm, String[] args) {

    // Acepta 0 argumentos o un flag "--ord=asc|desc"
    if (args.length == 0) {
      return fsm.ls(null);
    }
    if (args.length == 1 && args[0].startsWith("--ord=")) {
      String val = args[0].substring("--ord=".length());
      return fsm.ls(val);
    }
    return "Error: ls accepts at most the flag --ord=asc|desc.";
  }
}
