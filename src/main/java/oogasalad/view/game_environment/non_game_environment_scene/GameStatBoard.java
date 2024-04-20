package oogasalad.view.game_environment.non_game_environment_scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.model.api.PlayerRecord;

public class GameStatBoard {

  private final String playerText = "Player ";
  private final String playerScoreSeparator = ": ";
  private final String turnText = "Turn: ";
  private final String roundText = "Round: ";
  private final String scoreText = "Score";
  private final String scoreListStyleTag = "score-list";
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
    Text scoreTitle = createScoreTitle();
    createScoreListDisplay(players);
    createTurnDisplay(turn);
    createRoundDisplay(round);

    statContainer.getChildren().addAll(roundDisplay, turnDisplay, scoreTitle, scoreListDisplay);
    statContainer.setPrefSize(500, 500);
    statContainer.setLayoutX(500);
    statContainer.setLayoutY(500);
  }

  private void createTurnDisplay(int turn) {
    turnDisplay = new Text(turnText + turn);
  }

  private void updateTurn(int turn) {
    turnDisplay.setText(turnText + turn);
  }

  private void createRoundDisplay(int round) {
    roundDisplay = new Text(roundText + round);
  }

  private void updateRound(int round) {
    roundDisplay = new Text(roundText + round);
  }

  private void updateScore(List<PlayerRecord> players) {
    Map<Integer, Double> scoreMap = getPlayersIdsAndScoresFromRecord(players);
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

  private Text createScoreTitle() {
    Text scoreTitle = new Text(scoreText);
    return scoreTitle;
  }

  private Map<Integer, Double> getPlayersIdsAndScoresFromRecord(List<PlayerRecord> players) {
    Map<Integer, Double> scoreMap = new TreeMap<>();
    for (PlayerRecord p : players) {
      scoreMap.put(p.playerId(), p.score());
    }
    return scoreMap;
  }
}
