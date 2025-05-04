package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.results.FsResult;

public class FileSystemManager {
  private FileSystem current;

  public FileSystemManager() {
    this.current = new FileSystem();
  }

  public String ls(String ord) {
    FsResult res = current.ls(ord);
    return res.getMessage();
  }

  public String cd(String path) {
    FsResult res = current.cd(path);
    if (res.isSuccess()) current = res.nextState();
    return res.getMessage();
  }

  public String mkdir(String name) {
    FsResult res = current.mkdir(name);
    if (res.isSuccess()) {
      current = res.nextState();
    }
    return res.getMessage();
  }

  public String touch(String name) {
    FsResult res = current.touch(name);
    if (res.isSuccess()) current = res.nextState();
    return res.getMessage();
  }

  public String pwd() {
    return current.pwd().getMessage();
  }

  public String rm(String name, boolean recursive) {
    FsResult res = current.rm(name, recursive);
    if (res.isSuccess()) current = res.nextState();
    return res.getMessage();
  }
}
