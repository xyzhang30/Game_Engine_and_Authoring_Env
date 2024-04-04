package oogasalad.view;

import java.util.List;
import javafx.scene.Scene;
import oogasalad.model.api.GameRecord;
import oogasalad.view.Screen.GameScreen;
import oogasalad.view.Screen.MenuScreen;
import oogasalad.view.Screen.TitleScreen;
import oogasalad.view.Screen.TransitionScreen;
import oogasalad.view.VisualElements.CompositeElement;

public class SceneManager {
  private CompositeElement compositeElement;
  private GameScreen gameScreen;
  private int currentRound = 0;


  public SceneManager() {
  }

  public void update(GameRecord gameRecord) {
    compositeElement.update(gameRecord.collidables());
    checkEndRound(gameRecord);
  }

  public boolean notMoving(GameRecord gameRecord){
    return gameRecord.staticState(); //will be added to record eventually
  }

  public Scene makeTitleScreen(Controller controller){
      TitleScreen titleScreen = new TitleScreen(controller);
      return titleScreen.getScene();
  }
  public Scene makeMenuScreen(List<String> titles, Controller controller){
    MenuScreen menuScreen = new MenuScreen(titles, controller);
    return menuScreen.getScene();
  }
  public Scene makeGameScreen(Controller controller, CompositeElement compositeElement){
      gameScreen = new GameScreen(controller, compositeElement);
      return gameScreen.getScene();
  }

  public void enableHitting(){
    gameScreen.enableHitting();
  }


  public void checkEndRound(GameRecord gameRecord) {
    if (gameRecord.round() != currentRound) {
      currentRound = gameRecord.round();
      gameScreen.endRound(true);
    }
  }


  public Scene makeTransitionScreen(){
    TransitionScreen transitionScreen = new TransitionScreen();
    return transitionScreen.getScene();
  }
}
