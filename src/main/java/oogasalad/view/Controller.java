package oogasalad.view;

import java.util.ArrayList;
import java.util.List;
import oogasalad.Pair;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.api.GameRecord;
import oogasalad.view.Screen.ScreenType;

/**
 * Controller class handles communications between model and view.  This class holds manager class
 * instances to delegate handling the information received from the model.
 *
 * @author Jordan Haytaian
 */
public class Controller {

  private final GameEngine gameEngine;
  private final CollisionManager collisionManager;
  private final SceneManager sceneManager;

  public Controller(int id) {
    gameEngine = new GameEngine(id);
    collisionManager = new CollisionManager();
    sceneManager = new SceneManager();
  }

  /**
   * Waits until status is set to menu screen indicating that the play button has been pressed,
   * calls sceneManager to make and display a title scene with a given list of titles
   */
  public void startTitleListening() {
    ScreenType screenType = sceneManager.getScreenType();
    while (screenType != ScreenType.MENU_SCREEN) {
      screenType = sceneManager.getScreenType();
    }

    sceneManager.makeMenuScreen(getGameTitles());

  }

  private void startMenuListening(){
    ScreenType screenType = sceneManager.getScreenType();
    while (screenType != ScreenType.GAME_SCREEN) {
      screenType = sceneManager.getScreenType();
    }

    sceneManager.makeGameScreen();
  }

  private void runGame() {
    while (sceneManager.getScreenType() == ScreenType.GAME_SCREEN) {
      GameRecord gameRecord = gameEngine.update();
      sceneManager.update(gameRecord);
      List<Pair> collisionList = collisionManager.getIntersections();
      gameEngine.handleCollisions(collisionList);

      if (sceneManager.notMoving()) {
        //listen for hit
        //true if the ball is not moving
      }
    }
  }

  /**
   * Gets the titles of all available games to play
   *
   * @return a list of the game titles
   */
  public List<String> getGameTitles() {
    //TODO: Add parsing functionality
    List<String> gameTitles = new ArrayList<>();
    gameTitles.add("Mini Golf");
    return gameTitles;
  }

}
