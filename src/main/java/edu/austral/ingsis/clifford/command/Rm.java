package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.FileSystemManager;

public final class Rm implements Command {
  @Override
  public String execute(FileSystemManager fsm, String[] args) {
    boolean recursive = false;
    String target = null;
    for (String a : args) {
      if (a.equals("--recursive")) recursive = true;
      else target = a;
    }
    if (target == null) return "Error: rm expects a file or directory name.";
    return fsm.rm(target, recursive);
  }
}
