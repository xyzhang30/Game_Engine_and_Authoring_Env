package oogasalad.view.game_environment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import oogasalad.view.database.Leaderboard;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaderboardTest extends DukeApplicationTest {

  @Test
  public void testSetLeaderboard() {
    Leaderboard leaderboard = new Leaderboard();

    ListView<String> scoresListView = new ListView<>();

    ObservableList<String> scores = FXCollections.observableArrayList("Player1: 100", "Player2: 90", "Player3: 80");

    leaderboard.saveGameScores(scores);

    leaderboard.setLeaderboard(scoresListView);

    ObservableList<String> displayedScores = scoresListView.getItems();

    assertEquals(scores, displayedScores);
  }
}
