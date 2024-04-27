package oogasalad.view.database;

import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import oogasalad.view.controller.GameController;

/**
 * Manages and displays the leaderboard in the user interface, showing player scores for specific games.
 * This class is responsible for fetching, sorting, and updating the display of player scores using a ListView,
 * leveraging the GameScore record to encapsulate score data.
 *
 * @author Doga Ozmen
 */
public class Leaderboard {
  private GameController gameController;
  private ListView<String> leaderboardListView = new ListView<>();

  /**
   * Constructs a Leaderboard with a reference to the GameController to fetch formatted score data.
   * @param controller The GameController that provides access to formatted game scores.
   */
  public Leaderboard(GameController controller) {
    this.gameController = controller;
  }

  /**
   * Updates the leaderboard display for a specified game by fetching formatted scores and
   * displaying them in the ListView.
   * @param gameName The name of the game for which the leaderboard should be updated.
   */
  public void updateLeaderboard(String gameName) {
    ObservableList<String> formattedScores = gameController.getFormattedScoresForLeaderboard(gameName);
    leaderboardListView.setItems(formattedScores);
  }

  /**
   * Provides the ListView for integration into the UI, allowing for direct inclusion in the application's layout.
   * This ListView displays the sorted and formatted scores.
   * @return The ListView containing the formatted scores.
   */
  public ListView<String> gegtLeaderboardListView() {
    return leaderboardListView;
  }
}
