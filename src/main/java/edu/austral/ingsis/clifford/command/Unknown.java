package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.FileSystemManager;

public final class Unknown implements Command {
  private final String name;

  public Unknown(String name) {
    this.name = name;
  }

  @Override
  public String execute(FileSystemManager fsm, String[] args) {
    return "Error: unknown command '" + name + "'.";
  }
}
