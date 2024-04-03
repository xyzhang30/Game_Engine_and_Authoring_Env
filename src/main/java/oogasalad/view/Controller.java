package oogasalad.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller class handles communications between model and view.  This class holds manager class
 * instances to delegate handling the information received from the model.
 *
 * @author Jordan Haytaian
 */
public class Controller {

  //private final GameEngine gameEngine;
  //private final CollisionManager collisionManager;
  private final SceneManager sceneManager;
  private final Window window;

  public Controller(Stage stage, int id) {
    sceneManager = new SceneManager();
    Scene title = sceneManager.makeTitleScreen(this);
    window = new Window(stage, title, id);
    //gameEngine = new GameEngine(id);
    //collisionManager = new CollisionManager();

  }

  public void openMenuScreen() {
    Scene menu = sceneManager.makeMenuScreen(getGameTitles(), this);
    window.changeScene(menu);
  }

  public void startGamePlay(){}

//  private void runGame() {
//    while (sceneManager.getScreenType() == ScreenType.GAME_SCREEN) {
//      GameRecord gameRecord = gameEngine.update();
//      sceneManager.update(gameRecord);
//      List<Pair> collisionList = collisionManager.getIntersections();
//      gameEngine.handleCollisions(collisionList);
//
//      if (sceneManager.notMoving(gameRecord)) {
//        //listen for hit
//        //true if the ball is not moving
//      }
//    }
//  }

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
