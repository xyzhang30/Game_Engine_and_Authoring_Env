package oogasalad.view.scene_management.scene_element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import oogasalad.model.api.PlayerRecord;

/**
 * Manages the display and updates of game status, including players' scores, current round, and
 * current turn. This class is responsible for handling the visual representation of the game status
 * in the user interface.
 *
 * @author Jordan Haytaian
 */
public class GameStatusManager {
  private final String playerScoreSeparator = ": ";
  private String turnText;
  private String roundText;
  private ListView<String> scoreListDisplay;
  private Text turnDisplay;
  private Text roundDisplay;

  /**
   * Sets the Text element for displaying the current round
   *
   * @param roundDisplay Text element for displaying the current round.
   */
  public void setRoundText(Text roundDisplay) {
    this.roundDisplay = roundDisplay;
    roundText = roundDisplay.getText();
  }

  /**
   * Sets the Text element for displaying the current turn.
   *
   * @param turnDisplay Text element for displaying the current turn.
   */
  public void setTurnText(Text turnDisplay) {
    this.turnDisplay = turnDisplay;
    turnText = turnDisplay.getText();
  }

  /**
   * Sets the ListView for displaying players' scores.
   *
   * @param scoreListDisplay ListView for displaying players' scores.
   */
  public void setScoreList(ListView<String> scoreListDisplay) {
    this.scoreListDisplay = scoreListDisplay;
  }

  /**
   * Updates the score, turn, and round display
   *
   * @param players list current player records which contain score info
   * @param turn    id of player whose turn it is
   * @param round   current round
   */
  public void update(List<PlayerRecord> players, int turn, int round, Map<Integer,
      String> playerMap) {
    updateScore(players, playerMap);
    updateTurn(turn);
    updateRound(round);
  }

  private void updateTurn(int turn) {
    turnDisplay.setText(turnText + playerText + turn);
  }

  private void updateRound(int round) {
    roundDisplay.setText(roundText + round);
  }

  private void updateScore(List<PlayerRecord> players, Map<Integer,
      String> playerMap) {
    scoreListDisplay.getItems().clear();
    scoreListDisplay.setItems(createScoreListItems(players, playerMap));
  }

  private ObservableList<String> createScoreListItems(List<PlayerRecord> players, Map<Integer,
        String> playerMap) {
    List<String> scores = new ArrayList<>();
    for (PlayerRecord player : players) {
      scores.add(playerMap.get(player.playerId()) + playerScoreSeparator + player.score());
    }
    return FXCollections.observableList(scores);
  }
}
