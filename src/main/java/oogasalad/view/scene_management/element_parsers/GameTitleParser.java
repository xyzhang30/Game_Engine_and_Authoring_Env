package oogasalad.view.scene_management.element_parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Parses and retrieves a list of game titles from a specified directory.
 *
 * <p>This class is responsible for scanning a directory of playable games and extracting
 * the titles of game files, excluding directories and files that contain a specified identifier
 * (e.g., "test") in their names. The class returns an observable list of game titles, where each
 * title is derived from the file name, excluding its extension. This allows the application to
 * dynamically load and display available game titles for users to select from.
 *
 * @author Jordan Haytaian, Judy He
 **/
public class GameTitleParser {

  private final String newGameDir = "data/playable_games";
  private final String resumeGameDir = "data/resume_game";
  private final String testFileIdentifier = "test";

  /**
   * Retrieves a list of new game titles from the specified directory.
   * <p>
   * This method scans the directory specified by `playableGameDir` and collects all files that are
   * not directories. It then filters out files that contain the specified `testFileIdentifier` in
   * their names (case-insensitive) and adds the file names, excluding their extensions, to a list
   * of game titles. The method returns an observable list containing these game titles.
   *
   * @return An observable list of strings, where each string is the title of a new game file in the
   * specified directory, excluding the file extension.
   */
  public ObservableList<String> getNewGameTitles() {
    return getTitles(newGameDir);
  }

  /**
   * Retrieves a list of saved game titles from a predefined directory.
   * <p>
   * This method scans the directory specified for saved games and collects all files that are
   * not directories. It then adds the file names, excluding their extensions, to a list of game
   * titles. The method returns an observable list containing these game titles.
   *
   * @return An observable list of strings, where each string is the title of a saved game file in the
   * predefined directory, excluding the file extension.
   */
  public ObservableList<String> getSavedGameTitles() {
    return getTitles(resumeGameDir);
  }

  private ObservableList<String> getTitles(String dirPath){
    Set<String> games = Stream.of(new File(dirPath).listFiles())
        .filter(file -> !file.isDirectory())
        .map(File::getName)
        .collect(Collectors.toSet());

    List<String> gameTitles = new ArrayList<>();
    for (String filePath : games) {
      if (!filePath.toLowerCase().contains(testFileIdentifier)) {
        gameTitles.add(filePath.substring(0, filePath.indexOf(".")));
      }
    }

    return FXCollections.observableList(gameTitles);
  }

}
