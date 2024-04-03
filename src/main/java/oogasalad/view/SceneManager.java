package oogasalad.view;

import java.util.List;
import javafx.application.Platform;
import javafx.scene.Scene;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.CollidableRecord;
import oogasalad.view.Screen.*;

public class SceneManager {
  private Scene mainScene;
  private CompositeElement compositeElement;
  private ScreenType currentScreenType;


  public SceneManager(Scene mainScene) {
    this.mainScene = mainScene;
    this.compositeElement = new CompositeElement();
    makeTitleScreen();

  }

  public void update(GameRecord gameRecord) {
    for (CollidableRecord collidable : gameRecord.collidables()) {
      compositeElement.updateShape(collidable.id(), collidable.x(), collidable.y(), collidable.visible());
    }
  }

  public boolean notMoving(GameRecord gameRecord){
    return gameRecord.staticState(); //will be added to record eventually
  }

  public ScreenType getScreenType() {
    return currentScreenType;
  }

  public void makeTitleScreen(){
    Platform.runLater(() -> {
      TitleScreen titleScreen = new TitleScreen(this);
      mainScene.setRoot(titleScreen);
      currentScreenType = ScreenType.TITLE_SCREEN;
    });
  }
  public void makeMenuScreen(List<String> titles){
    Platform.runLater(() -> {
      MenuScreen menuScreen = new MenuScreen(titles, this);
      mainScene.setRoot(menuScreen);
      currentScreenType = ScreenType.MENU_SCREEN;
    });
  }
  public void makeGameScreen(){
    Platform.runLater(() -> {
      GameScreen gameScreen = new GameScreen(compositeElement);
      mainScene.setRoot(gameScreen);
      currentScreenType = ScreenType.GAME_SCREEN;
    });
  }

//  public void makeTransitionScreen(){
//    Platform.runLater(() -> {
//      TransitionScreen transitionScreen = new TransitionScreen();
//      mainScene.setRoot(transitionScreen);
//      currentScreenType = ScreenType.TRANSITION_SCREEN;
//    });
//  }

  public void setScreenTypeMenu(){
    currentScreenType = ScreenType.MENU_SCREEN;
  }
  public void setScreenTypeGame(){currentScreenType = ScreenType.GAME_SCREEN;}
  public void setScreenTypeTransition(){currentScreenType = ScreenType.TRANSITION_SCREEN;}

}
