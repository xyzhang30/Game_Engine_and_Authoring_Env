package oogasalad.view;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.scene.Group;
import javafx.scene.Scene;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;
import oogasalad.view.Controlling.GameController;
import oogasalad.view.GameScreens.GameScreen;
import oogasalad.view.GameScreens.MenuScreen;
import oogasalad.view.GameScreens.TitleScreen;
import oogasalad.view.GameScreens.TransitionScreen;
import oogasalad.view.VisualElements.CompositeElement;

/**
 * Manages different screens (scenes) within the game, such as the title screen, menu screen, game screen, and transition screen.
 * It updates and transitions between screens based on game state and player interactions.
 *
 * @author Doga Ozmen
 */
public class SceneManager {

  private final Scene scene;
  private CompositeElement compositeElement;
  private GameScreen gameScreen;
  private int currentRound = 1;


  public SceneManager() {
    scene = new Scene(new Group());
  }

  public Scene getScene() {
    return scene;
  }

  public void update(GameRecord gameRecord) {
    compositeElement.update(gameRecord.gameObjectRecords());
    Map<Integer, Double> scoreMap = new TreeMap<>();
    for(PlayerRecord p : gameRecord.players()) {
      scoreMap.put(p.playerId(), p.score());
    }
    updateScoreTurnBoard(scoreMap, gameRecord.turn(), gameRecord.round());
    checkEndRound(gameRecord);
  }

  public void makeTitleScreen(GameController controller) {
    TitleScreen titleScreen = new TitleScreen(controller);
    scene.setRoot(titleScreen.getRoot());
  }

  public void makeMenuScreen(List<String> titles, GameController controller) {
    MenuScreen menuScreen = new MenuScreen(titles, controller);
    scene.setRoot(menuScreen.getRoot());
  }

  public void makeGameScreen(GameController controller, CompositeElement compositeElement) {
    this.compositeElement = compositeElement;
    gameScreen = new GameScreen(controller, compositeElement);
    scene.setRoot(gameScreen.getRoot());
    gameScreen.initiateListening(scene);
  }

  public void enableHitting() {
    gameScreen.enableHitting();
  }


  public void checkEndRound(GameRecord gameRecord) {
    if (gameRecord.round() != currentRound) {
      currentRound = gameRecord.round();
    }
    if (gameRecord.gameOver()) {
      gameScreen.endRound(true);
    }
  }


  public void makeTransitionScreen() {
    TransitionScreen transitionScreen = new TransitionScreen();
    scene.setRoot(transitionScreen.getRoot());
  }

  public void updateScoreTurnBoard(Map<Integer, Double> scoreMap, int turn, int round) {
    gameScreen.updateScoreBoard(scoreMap);
    gameScreen.updateTurnBoard(turn, round);
  }
}
