package oogasalad.view;

import java.util.List;
import javafx.application.Platform;
import javafx.scene.Scene;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.CollidableRecord;
import oogasalad.view.Screen.*;

public class SceneManager {
  private CompositeElement compositeElement;


  public SceneManager() {
    this.compositeElement = new CompositeElement();
  }

  public void update(GameRecord gameRecord) {
    for (CollidableRecord collidable : gameRecord.collidables()) {
      compositeElement.updateShape(collidable.id(), collidable.x(), collidable.y(), collidable.visible());
    }
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
  public Scene makeGameScreen(){
      GameScreen gameScreen = new GameScreen(compositeElement);
      return gameScreen.getScene();
  }

  public void makeTransitionScreen(){
//    TransitionScreen transitionScreen = new TransitionScreen();
//    return TransitionScreen.getScene();
  }
}
