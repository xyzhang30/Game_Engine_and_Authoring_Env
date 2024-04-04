package oogasalad.view;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import oogasalad.model.api.GameRecord;
import oogasalad.view.Screen.GameScreen;
import oogasalad.view.Screen.MenuScreen;
import oogasalad.view.Screen.TitleScreen;
import oogasalad.view.Screen.TransitionScreen;
import oogasalad.view.VisualElements.CompositeElement;

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
    compositeElement.update(gameRecord.collidables());
    checkEndRound(gameRecord);
  }

  public boolean notMoving(GameRecord gameRecord) {
    return gameRecord.staticState(); //will be added to record eventually
  }

  public void makeTitleScreen(Controller controller) {
    TitleScreen titleScreen = new TitleScreen(controller);
    scene.setRoot(titleScreen.getRoot());
  }

  public void makeMenuScreen(List<String> titles, Controller controller) {
    MenuScreen menuScreen = new MenuScreen(titles, controller);
    scene.setRoot(menuScreen.getRoot());
  }

  public void makeGameScreen(Controller controller, CompositeElement compositeElement) {
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

  public void updateScoreBoard(double score) {
    //gameScreen.updateScoreBoard(score);
  }
}
