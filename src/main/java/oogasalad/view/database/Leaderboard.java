package oogasalad.view.database;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import oogasalad.view.controller.DatabaseController;

/**
 * Manages and displays the leaderboard in the user interface, showing player scores for specific games.
 * This class is responsible for fetching and displaying the formatted scores in a ListView.
 *
 * @author Doga Ozmen
 */
public class Leaderboard {
  private DatabaseController databaseController;
  private ObservableList<String> leaderboardScores;
  private ListView<String> leaderboardListView;

  /**
   * Constructor that initializes the leaderboard's ObservableList and ListView.
   */
  public Leaderboard(DatabaseController databaseController) {
    this.databaseController = databaseController;
    this.leaderboardScores = FXCollections.observableArrayList();
    this.leaderboardListView = new ListView<>(leaderboardScores);
  }

  /**
   * Updates the leaderboard display for a specified game by fetching formatted scores and
   * displaying them in the ListView.
   * @param gameName The name of the game for which the leaderboard should be updated.
   */
  public void updateLeaderboard(String gameName) {
    List<String> scores = databaseController.getFormattedScoresForLeaderboard(gameName);
    leaderboardScores.setAll(scores);
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
