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
   * Runs the game by sending and receiving info from scene manager
   */
  public void runGame() {
      while (sceneManager.getScreenType() == ScreenType.GAME_SCREEN) {
        GameRecord gameRecord = gameEngine.update();
        sceneManager.update(gameRecord);
        List<Pair> collisionList = collisionManager.getIntersections();
        gameEngine.handleCollisions(collisionList);

        if(sceneManager.notMoving()){
          //listen for hit
          //true if the ball is not moving
        }
      }
  }

  /**
   * Gets the titles of all available games to play
   * @return a list of the game titles
   */
  public List<String> getGameTitles(){
    //TODO: Add parsing functionality
    List<String> gameTitles = new ArrayList<>();
    gameTitles.add("Mini Golf");
    return gameTitles;
  }

}
