package oogasalad.view.database;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * Manages and displays the leaderboard in the user interface, showing player scores for specific
 * games. This class is responsible for fetching and displaying the formatted scores in a ListView.
 *
 * @author Doga Ozmen
 */
public class Leaderboard {

  private ObservableList<String> leaderboardScores;

  /**
   * Constructor that initializes the leaderboard's ObservableList and ListView.
   */
  public Leaderboard() {
  }

  /**
   * Updates the leaderboard display for a specified game by fetching formatted scores and
   * displaying them in the ListView.
   */
  public void setLeaderboard(ListView<String> scoresListView) {
    scoresListView.setItems(leaderboardScores);
  }

  public void saveGameScores(ObservableList<String> score) {
    leaderboardScores = score;
  }

}
