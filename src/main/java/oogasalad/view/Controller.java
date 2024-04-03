package oogasalad.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.Pair;
import oogasalad.model.api.GameRecord;
import oogasalad.model.gameengine.GameEngine;

/**
 * Controller class handles communications between model and view.  This class holds manager class
 * instances to delegate handling the information received from the model.
 *
 * @author Jordan Haytaian
 */
public class Controller {

  private GameEngine gameEngine = null;
  private final CollisionManager collisionManager;
  private final SceneManager sceneManager;
  private final AnimationManager animationManager;
  private final Stage stage;

  public Controller(Stage stage) {
    sceneManager = new SceneManager();
    animationManager = new AnimationManager();
    Scene title = sceneManager.makeTitleScreen(this);
    this.stage = stage;
    stage.setScene(title);
    collisionManager = new CollisionManager();

  }

  /**
   * Creates and displays menu screen
   */
  public void openMenuScreen() {
    Scene menu = sceneManager.makeMenuScreen(getGameTitles(), this);
    stage.setScene(menu);
  }

  /**
   * Starts the selected game by loading necessary back end components, creating the scene, and
   * starting the animation
   *
   * @param selectedGame the game selected to play
   */
  public void startGamePlay(String selectedGame) {
    //new FrontendParser(selectedGame);
    //gameEngine = new GameEngine(selectedGame);
    //create compositeElement from css files and pass to sceneManager
    Scene gameScene = sceneManager.makeGameScreen(this);
    //pass compositeElement to collision manager
    stage.setScene(gameScene);
    animationManager.runAnimation(this);
  }

  /**
   * Method to update visual game elements
   * @param timeStep timestep for animation
   * @return boolean indicating if round is over
   */
  public boolean runGame(double timeStep) {
//    GameRecord gameRecord = gameEngine.update(timeStep);
//    sceneManager.update(gameRecord);
//    List<Pair> collisionList = collisionManager.getIntersections();
//    gameEngine.handleCollisions(collisionList, timeStep);
//    gameEngine.update(timeStep);
//
//    if (sceneManager.notMoving(gameRecord)) {
//      sceneManger.enableHitting();
//    }
//    //return if game is over
    return true;
  }

  public void hitPointScoringObject(double fractionalVelocity){
    //gameEngine.applyInitialVelocity();
  }


  /**
   * Gets the titles of all available games to play
   *
   * @return a list of the game titles
   */
  public List<String> getGameTitles() {
    //TODO: Add parsing functionality
    List<String> gameTitles = new ArrayList<>();
    gameTitles.add("sampleMiniGolf");
    return gameTitles;
  }

}
