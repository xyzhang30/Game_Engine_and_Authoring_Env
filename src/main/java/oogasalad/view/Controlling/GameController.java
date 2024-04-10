package oogasalad.view.Controlling;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import oogasalad.Pair;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.ViewCollidableRecord;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameparser.GameLoaderView;
import oogasalad.view.AnimationManager;
import oogasalad.view.CollisionManager;
import oogasalad.view.SceneManager;
import oogasalad.view.VisualElements.CompositeElement;


/**
 * GameController class handles communications between model and view.  This class holds manager class
 * instances to delegate handling the information received from the model.
 *
 * @author Jordan Haytaian
 */
public class GameController {

  private final CollisionManager collisionManager;
  private final SceneManager sceneManager;
  private final AnimationManager animationManager;
  private GameEngine gameEngine;
  private GameLoaderView gameLoaderView;

  public GameController() {
    sceneManager = new SceneManager();
    sceneManager.makeTitleScreen(this);
    animationManager = new AnimationManager();
    collisionManager = new CollisionManager();
  }

  public Scene getScene(){
    return sceneManager.getScene();
  }

  /**
   * Creates and displays menu screen
   */
  public void openMenuScreen() {
    sceneManager.makeMenuScreen(getGameTitles(), this);
  }

  public void openTransitionScreen() {
    sceneManager.makeTransitionScreen();
  }

  /**
   * Starts the selected game by loading necessary back end components, creating the scene, and
   * starting the animation
   *
   * @param selectedGame the game selected to play
   */
  public void startGamePlay(String selectedGame) {
    gameLoaderView = new GameLoaderView(selectedGame);
    gameEngine = new GameEngine(selectedGame);
    CompositeElement compositeElement = createCompositeElementFromGameLoader();
    sceneManager.makeGameScreen(this, compositeElement);
    collisionManager.setNewCompositeElement(compositeElement);
  }

  /**
   * Method to update visual game elements
   *
   * @param timeStep timestep for animation
   * @return boolean indicating if round is over
   */
  public boolean runGame(double timeStep) {
    GameRecord gameRecord = gameEngine.update(timeStep);
    if (gameRecord.staticState()) {
      animationManager.pauseAnimation();
    }
    sceneManager.update(gameRecord);
    sceneManager.updateScoreBoard(gameRecord.players().get(0).score());

    //List<Pair> collisionList = collisionManager.getIntersections();
//    Map<Pair, String> collisionType = collisionManager.getIntersectionsMap();

   // GameRecord gameRecord2 = gameEngine.handleCollisions(collisionList, timeStep);
   // sceneManager.update(gameRecord2);
    if (sceneManager.notMoving(gameRecord)) {
      sceneManager.enableHitting();
    }

    return true;
  }

  /**
   * Sends velocity and angle to back end to simulate hitting point scoring object
   *
   * @param fractionalVelocity velocity as fraction of maxVelocity
   */
  public void hitPointScoringObject(double fractionalVelocity, double angle) {
    gameEngine.applyInitialVelocity(700 * fractionalVelocity, angle, 8); // The 8 has been hard
    // coded!
    animationManager.runAnimation(this);
  }


  /**
   * Gets the titles of all available games to play
   *
   * @return a list of the game titles
   */
  public List<String> getGameTitles() {
    //TODO: Add parsing functionality
    List<String> gameTitles = new ArrayList<>();
    gameTitles.add("singlePlayerMiniGolf");
    return gameTitles;
  }

  private CompositeElement createCompositeElementFromGameLoader() {
    List<ViewCollidableRecord> recordList = gameLoaderView.getViewCollidableInfo();
    return new CompositeElement(recordList);
  }

}
