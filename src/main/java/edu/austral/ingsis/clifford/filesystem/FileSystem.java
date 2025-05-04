package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.results.FsResult;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class FileSystem {

  private final DirectoryNode root;
  private final DirectoryNode current;

  public FileSystem() {
    this.root = new DirectoryNode("/");
    this.current = root;
  }

  private FileSystem(DirectoryNode root, DirectoryNode current) {
    this.root = root;
    this.current = current;
  }

  public DirectoryNode getRoot() {
    return root;
  }

  public DirectoryNode getCurrent() {
    return current;
  }

  public String getCurrentPath() {
    return buildPath(root, current, "");
  }

  public FsResult ls(String ord) {
    String flag = (ord == null) ? "" : ord;
    if (!flag.isEmpty() && !flag.equals("asc") && !flag.equals("desc")) {
      return FsResult.error("Error: --ord must be asc or desc.");
    }
    List<String> names = new ArrayList<>();
    for (FileSystemNode child : current.getChildren()) {
      names.add(child.getName());
    }
    if (names.isEmpty()) {
      return FsResult.ok("", this);
    }
    if (flag.equals("asc")) names.sort(Comparator.naturalOrder());
    if (flag.equals("desc")) names.sort(Comparator.reverseOrder());
    String listing = String.join(" ", names);
    return FsResult.ok(listing, this);
  }

  public FsResult cd(String cdPath) {
    if (cdPath == null || cdPath.isBlank())
      return FsResult.error("Error: cd expects a directory path.");
    DirectoryNode target = resolveTarget(cdPath);
    if (target == null) return FsResult.error("'" + cdPath + "' directory does not exist");
    return FsResult.ok(
        "moved to directory '" + target.getName() + "'", new FileSystem(root, target));
  }

  private DirectoryNode resolveTarget(String path) {
    if (path.startsWith("/")) {
      return navigate(root, path.substring(1).split("/"));
    }
    if (path.equals(".")) return current;
    if (path.equals("..")) {
      DirectoryNode parent = findParent(root, current);
      return parent == null ? current : parent;
    }
    if (!path.contains("/")) {
      return findChildDir(current, path);
    }
    return navigate(current, path.split("/"));
  }

  private static DirectoryNode navigate(DirectoryNode start, String[] segments) {
    DirectoryNode node = start;
    for (String part : segments) {
      if (part.isBlank()) continue;
      node = findChildDir(node, part);
      if (node == null) return null;
    }
    return node;
  }

  public FsResult mkdir(String name) {
    if (name.contains("/") || name.contains(" ")) {
      return FsResult.error("Error: Invalid directory name. It must not contain '/' or spaces.");
    }
    for (FileSystemNode child : current.getChildren()) {
      if (child.getName().equals(name)) {
        return FsResult.error("Error: Directory already exists.");
      }
    }
    DirectoryNode newDir = new DirectoryNode(name);
    DirectoryNode newCurrent = current.withChild(newDir);
    DirectoryNode newRoot = replaceSubtree(root, current, newCurrent);
    FileSystem newFs = new FileSystem(newRoot, newCurrent);
    return FsResult.ok("'" + name + "' directory created", newFs);
  }

  public FsResult touch(String name) {
    if (name.contains("/") || name.contains(" "))
      return FsResult.error("Error: Invalid file name. It must not contain '/' or spaces.");
    for (FileSystemNode child : current.getChildren())
      if (child.getName().equals(name)) return FsResult.error("Error: File already exists.");

    FileNode newFile = new FileNode(name);
    DirectoryNode newCurr = current.withChild(newFile);
    DirectoryNode newRoot = replaceSubtree(root, current, newCurr);
    return FsResult.ok("'" + name + "' file created", new FileSystem(newRoot, newCurr));
  }

  public FsResult pwd() {
    return FsResult.ok(getCurrentPath(), this);
  }

  private static DirectoryNode findChildDir(DirectoryNode parent, String name) {
    for (FileSystemNode child : parent.getChildren()) {
      if (child.isDirectory() && child.getName().equals(name)) return (DirectoryNode) child;
    }
    return null;
  }

  public FsResult rm(String name, boolean recursive) {
    if (name == null || name.isBlank()) return FsResult.error("Error: rm expects a name.");
    FileSystemNode victim = null;
    for (FileSystemNode child : current.getChildren()) {
      if (child.getName().equals(name)) {
        victim = child;
        break;
      }
    }
    if (victim == null) return FsResult.error("Error: '" + name + "' not found.");
    if (victim.isDirectory() && !recursive)
      return FsResult.error("cannot remove '" + name + "', is a directory");
    List<FileSystemNode> newKids = new ArrayList<>();
    for (FileSystemNode child : current.getChildren()) {
      if (child != victim) newKids.add(child);
    }
    DirectoryNode newCurrent = current.withChildren(newKids);
    DirectoryNode newRoot = replaceSubtree(root, current, newCurrent);
    FileSystem nextFs = new FileSystem(newRoot, newCurrent);
    return FsResult.ok("'" + name + "' removed", nextFs);
  }

  private static DirectoryNode replaceSubtree(
      DirectoryNode tree, DirectoryNode oldNode, DirectoryNode replacement) {

    if (tree == oldNode) return replacement;
    List<FileSystemNode> newKids = new ArrayList<>();
    for (FileSystemNode child : tree.getChildren()) {
      if (child.isDirectory()) {
        newKids.add(replaceSubtree((DirectoryNode) child, oldNode, replacement));
      } else {
        newKids.add(child);
      }
    }
    return tree.withChildren(newKids);
  }

  private static DirectoryNode findParent(DirectoryNode node, DirectoryNode target) {
    for (FileSystemNode child : node.getChildren()) {
      if (child == target) return node;
      if (child.isDirectory()) {
        DirectoryNode candidate = findParent((DirectoryNode) child, target);
        if (candidate != null) return candidate;
      }
    }
    return null;
  }

  private static String buildPath(DirectoryNode node, DirectoryNode target, String accumulated) {
    if (node == target) {
      if (accumulated.isEmpty()) return "/";
      return "/" + accumulated.substring(0, accumulated.length() - 1);
    }
    for (FileSystemNode child : node.getChildren()) {
      if (child.isDirectory()) {
        String path = buildPath((DirectoryNode) child, target, accumulated + child.getName() + "/");
        if (path != null) return path;
      }
    }
    return null;
  }
}
