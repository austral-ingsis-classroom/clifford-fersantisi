package edu.austral.ingsis.clifford.filesystem;

public class FileNode implements FileSystemNode {
  private final String name;

  public FileNode(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isDirectory() {
    return false;
  }
}
