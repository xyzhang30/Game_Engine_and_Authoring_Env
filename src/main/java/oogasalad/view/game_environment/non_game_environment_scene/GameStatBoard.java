package oogasalad.view.game_environment.non_game_environment_scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.model.api.PlayerRecord;

public class GameStatBoard {

  private final String playerText = "Player ";
  private final String playerScoreSeparator = ": ";
  private final String turnText = "Turn: ";
  private final String roundText = "Round: ";
  private final String scoreListStyleTag = "score-list";
  private final String textStyleTag = "game-text";
  private ListView<String> scoreListDisplay;
  private Text turnDisplay;
  private Text roundDisplay;
  private VBox statContainer;

  public GameStatBoard(List<PlayerRecord> players, int turn, int round) {
    createGameStatBoardDisplay(players, turn, round);
  }

  public VBox getContainer() {
    return statContainer;
  }

  public void update(List<PlayerRecord> players, int turn, int round) {
    updateScore(players);
    updateTurn(turn);
    updateRound(round);
  }

  private void createGameStatBoardDisplay(List<PlayerRecord> players, int turn, int round) {
    statContainer = new VBox();
    createScoreListDisplay(players);
    createTurnDisplay(turn);
    createRoundDisplay(round);

    statContainer.getChildren().addAll(roundDisplay, turnDisplay, scoreListDisplay);
    //TODO: Remove this hard coding
    statContainer.setLayoutX(1000);
    statContainer.setLayoutY(200);
    statContainer.setAlignment(Pos.CENTER);
  }

  private void createTurnDisplay(int turn) {
    turnDisplay = new Text(turnText + turn);
    turnDisplay.getStyleClass().add(textStyleTag);
  }

  private void updateTurn(int turn) {
    turnDisplay.setText(turnText + turn);
  }

  private void createRoundDisplay(int round) {
    roundDisplay = new Text(roundText + round);
    roundDisplay.getStyleClass().add(textStyleTag);
  }

  private void updateRound(int round) {
    roundDisplay = new Text(roundText + round);
  }

  private void updateScore(List<PlayerRecord> players) {
    scoreListDisplay.getItems().clear();
    scoreListDisplay.setItems(createScoreListItems(players));
  }

  private void createScoreListDisplay(List<PlayerRecord> players) {
    ObservableList<String> scores = createScoreListItems(players);
    scoreListDisplay = new ListView<>(scores);
    scoreListDisplay.setSelectionModel(null);
    scoreListDisplay.getStyleClass().add(scoreListStyleTag);
  }

  private ObservableList<String> createScoreListItems(List<PlayerRecord> players) {
    Map<Integer, Double> scoreMap = getPlayersIdsAndScoresFromRecord(players);
    List<String> scores = new ArrayList<>();
    for (Entry<Integer, Double> player : scoreMap.entrySet()) {
      scores.add(playerText + player.getKey() + playerScoreSeparator + player.getValue());
    }
    return FXCollections.observableList(scores);
  }

  private Map<Integer, Double> getPlayersIdsAndScoresFromRecord(List<PlayerRecord> players) {
    Map<Integer, Double> scoreMap = new TreeMap<>();
    for (PlayerRecord p : players) {
      scoreMap.put(p.playerId(), p.score());
    }
    return scoreMap;
  }
}
