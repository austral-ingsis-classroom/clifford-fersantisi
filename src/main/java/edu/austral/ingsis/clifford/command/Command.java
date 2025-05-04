package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.FileSystemManager;

public interface Command {
  String execute(FileSystemManager fsm, String[] args);
}
