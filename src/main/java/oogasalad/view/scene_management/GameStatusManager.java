package oogasalad.view.scene_management;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.model.api.PlayerRecord;

/**
 * Class to display and update game stats (players' scores, current round, current turn)
 *
 * @author Jordan Haytaian
 */
public class GameStatusManager {

  private final String playerText = "Player ";
  private final String playerScoreSeparator = ": ";
  private String turnText;
  private String roundText;
  private ListView<String> scoreListDisplay;
  private Text turnDisplay;
  private Text roundDisplay;


  public GameStatusManager() {
  }

  public void setRoundText(Text roundDisplay) {
    this.roundDisplay = roundDisplay;
    roundText = roundDisplay.getText();
  }

  public void setTurnText(Text turnDisplay) {
    this.turnDisplay = turnDisplay;
    turnText = turnDisplay.getText();
  }

  public void setScoreList(ListView<String> scoreListDisplay) {
    this.scoreListDisplay = scoreListDisplay;
    scoreListDisplay.setSelectionModel(null);
  }

  /**
   * Updates the score, turn, and round display
   *
   * @param players list current player records which contain score info
   * @param turn    id of player whose turn it is
   * @param round   current round
   */
  public void update(List<PlayerRecord> players, int turn, int round) {
    updateScore(players);
    updateTurn(turn);
    updateRound(round);
  }

  private void updateTurn(int turn) {
    turnDisplay.setText(turnText + playerText + turn);
  }

  private void updateRound(int round) {
    roundDisplay.setText(roundText + round);
  }

  private void updateScore(List<PlayerRecord> players) {
    scoreListDisplay.getItems().clear();
    scoreListDisplay.setItems(createScoreListItems(players));
  }

  private ObservableList<String> createScoreListItems(List<PlayerRecord> players) {
    List<String> scores = new ArrayList<>();
    for (PlayerRecord player : players) {
      scores.add(playerText + player.playerId() + playerScoreSeparator + player.score());
    }
    return FXCollections.observableList(scores);
  }
}
