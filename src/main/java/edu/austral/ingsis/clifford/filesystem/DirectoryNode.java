package edu.austral.ingsis.clifford.filesystem;

import java.util.ArrayList;
import java.util.List;

public final class DirectoryNode implements FileSystemNode {

  private final String name;
  private final List<FileSystemNode> children;

  public DirectoryNode(String name) {
    this.name = name;
    this.children = List.of();
  }

  private DirectoryNode(String name, List<FileSystemNode> children) {
    this.name = name;
    this.children = List.copyOf(children);
  }

  public DirectoryNode withChild(FileSystemNode child) {
    List<FileSystemNode> tmp = new ArrayList<>(children);
    tmp.add(child);
    return new DirectoryNode(name, tmp);
  }

  public DirectoryNode withChildren(List<FileSystemNode> newChildren) {
    return new DirectoryNode(name, newChildren);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isDirectory() {
    return true;
  }

  public List<FileSystemNode> getChildren() {
    return children;
  }
}
