package oogasalad.view.game_environment.scene_management;

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
public class GameStatBoard {
  private final SceneElementStyler sceneElementStyler;
  private final double screenWidth;
  private final double screenHeight;
  private final String playerText = "Player ";
  private final String playerScoreSeparator = ": ";
  private final String turnText = "Turn: ";
  private final String roundText = "Round: ";
  private final String scoreListStyleTag = "score-list";
  private final String textStyleTag = "game-text";
  private final String containerStyleTag = "stat-container";
  private ListView<String> scoreListDisplay;
  private Text turnDisplay;
  private Text roundDisplay;
  private VBox statContainer;

  /**
   * Constructor creates score, turn, and round display on call
   * @param players list current player records which contain score info
   * @param turn id of player whose turn it is
   * @param round current round
   * @param screenWidth screen width to be used for ratio scaling elements
   * @param screenHeight screen height to be used for ratio scaling elements
   */
  public GameStatBoard(List<PlayerRecord> players, int turn, int round, double screenWidth,
      double screenHeight, SceneElementStyler sceneElementStyler) {
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.sceneElementStyler = sceneElementStyler;
    createGameStatBoardDisplay(players, turn, round);
  }

  /**
   * Box to hold score, turn, and round display
   * @return VBox holding score, turn, and round display
   */
  public VBox getContainer() {
    return statContainer;
  }

  /**
   * Updates the score, turn, and round display
   * @param players list current player records which contain score info
   * @param turn id of player whose turn it is
   * @param round current round
   */
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
    statContainer.setLayoutX(0.88 * screenWidth);
    statContainer.setLayoutY(0.075 * screenHeight);
    statContainer.getStyleClass().add(containerStyleTag);
  }

  private void createTurnDisplay(int turn) {
    turnDisplay = new Text(turnText + playerText + turn);
    sceneElementStyler.style(turnDisplay, textStyleTag);
  }

  private void updateTurn(int turn) {
    turnDisplay.setText(turnText + playerText + turn);
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
    sceneElementStyler.style(scoreListDisplay, scoreListStyleTag);
  }

  private ObservableList<String> createScoreListItems(List<PlayerRecord> players) {
    List<String> scores = new ArrayList<>();
    for (PlayerRecord player : players) {
      scores.add(playerText + player.playerId() + playerScoreSeparator + player.score());
    }
    return FXCollections.observableList(scores);
  }
}
