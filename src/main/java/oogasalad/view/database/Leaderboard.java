package oogasalad.view.database;

import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import oogasalad.model.database.GameScore;
import java.util.List;
import java.util.stream.Collectors;
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
   * Constructs a Leaderboard with a reference to the GameController to fetch score data.
   * @param controller The GameController that provides access to the game scores.
   */
  public Leaderboard(GameController controller) {
    this.gameController = controller;
  }

  /**
   * Provides the ListView for integration into the UI, allowing for direct inclusion in the application's layout.
   * This ListView displays the sorted and formatted scores, reflecting each player's performance and game outcome.
   * @return The ListView containing the formatted scores.
   */
  public ListView<String> getLeaderboardListView() {
    return leaderboardListView;
  }
}
