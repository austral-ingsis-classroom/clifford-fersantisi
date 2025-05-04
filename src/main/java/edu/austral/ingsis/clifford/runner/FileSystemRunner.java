package edu.austral.ingsis.clifford.runner;

import edu.austral.ingsis.clifford.filesystem.FileSystemManager;
import edu.austral.ingsis.clifford.parser.CommandParser;
import edu.austral.ingsis.clifford.parser.ParsedCommand;
import java.util.ArrayList;
import java.util.List;

public class FileSystemRunner {
  private final FileSystemManager fsm = new FileSystemManager();
  private final CommandParser parser = new CommandParser();

  public List<String> executeCommands(List<String> commands) {
    List<String> results = new ArrayList<>();
    for (String command : commands) {
      ParsedCommand parsedCommand = parser.parse(command);
      String result = parsedCommand.getCommand().execute(fsm, parsedCommand.getArgs());
      results.add(result);
    }
    return results;
  }
}
