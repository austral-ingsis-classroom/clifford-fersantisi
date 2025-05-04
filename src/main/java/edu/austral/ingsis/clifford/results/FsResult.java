package edu.austral.ingsis.clifford.results;

import edu.austral.ingsis.clifford.filesystem.FileSystem;

public final class FsResult {

  private final boolean success;
  private final String message;
  private final FileSystem next;

  private FsResult(boolean success, String message, FileSystem next) {
    this.success = success;
    this.message = message;
    this.next = next;
  }

  public static FsResult ok(String msg, FileSystem fs) {
    return new FsResult(true, msg, fs);
  }

  public static FsResult error(String msg) {
    return new FsResult(false, msg, null);
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public FileSystem nextState() {
    return next;
  }
}
