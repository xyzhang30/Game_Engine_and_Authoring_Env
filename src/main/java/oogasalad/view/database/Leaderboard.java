package oogasalad.view.database;

import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import oogasalad.view.controller.DatabaseController;
import oogasalad.view.controller.GameController;

/**
 * Manages and displays the leaderboard in the user interface, showing player scores for specific games.
 *
 * @author Doga Ozmen
 */
public class Leaderboard {
  private DatabaseController databaseController;
  private ListView<String> leaderboardListView = new ListView<>();

  /**
   * Constructs a Leaderboard with a reference to the GameController to fetch formatted score data.
   * @param controller The GameController that provides access to formatted game scores.
   */
  public Leaderboard(DatabaseController controller) {
    this.databaseController = controller;
  }

  /**
   * Updates the leaderboard display for a specified game by fetching formatted scores and
   * displaying them in the ListView.
   * @param gameName The name of the game for which the leaderboard should be updated.
   */
  public void updateLeaderboard(String gameName) {
    ObservableList<String> formattedScores = databaseController.getFormattedScoresForLeaderboard(gameName);
    leaderboardListView.setItems(formattedScores);
  }

  /**
   * Provides the ListView for integration into the UI, allowing for direct inclusion in the application's layout.
   * This ListView displays the sorted and formatted scores.
   * @return The ListView containing the formatted scores.
   */
  public ListView<String> getLeaderboardListView() {
    return leaderboardListView;
  }
}
