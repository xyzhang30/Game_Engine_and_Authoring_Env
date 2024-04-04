package oogasalad.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.Pair;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.ViewCollidableRecord;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameparser.GameLoaderView;
import oogasalad.view.VisualElements.CompositeElement;


/**
 * Controller class handles communications between model and view.  This class holds manager class
 * instances to delegate handling the information received from the model.
 *
 * @author Jordan Haytaian
 */
public class Controller {

  private GameEngine gameEngine;
  private GameLoaderView gameLoaderView;
  private final CollisionManager collisionManager;
  private final SceneManager sceneManager;
  private final AnimationManager animationManager;
  private final Stage stage;

  public Controller(Stage stage) {
    sceneManager = new SceneManager();
    animationManager = new AnimationManager();
    this.stage = stage;
    sceneManager.makeTitleScreen(this);
    stage.setScene(sceneManager.getScene());
    collisionManager = new CollisionManager();

  }

  /**
   * Creates and displays menu screen
   */
  public void openMenuScreen() {
    sceneManager.makeMenuScreen(getGameTitles(), this);
    stage.setScene(sceneManager.getScene());
  }

  public void openTransitionScreen(){
    sceneManager.makeTransitionScreen();
    stage.setScene(sceneManager.getScene());
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
    stage.setScene(sceneManager.getScene());

    //animationManager.runAnimation(this);

  }

  /**
   * Method to update visual game elements
   * @param timeStep timestep for animation
   * @return boolean indicating if round is over
   */
  public boolean runGame(double timeStep) {
    GameRecord gameRecord = gameEngine.update(timeStep);
    sceneManager.update(gameRecord);
    List<Pair> collisionList = collisionManager.getIntersections();
    gameEngine.handleCollisions(collisionList, timeStep);
    gameEngine.update(timeStep);

    if (sceneManager.notMoving(gameRecord)) {
      sceneManager.enableHitting();
    }
    //return if game is over
    return true;
  }

  /**
   * Sends velocity and angle to back end to simulate hitting point scoring object
   * @param fractionalVelocity velocity as fraction of maxVelocity
   */
  public void hitPointScoringObject(double fractionalVelocity, double angle){
    //gameEngine.applyInitialVelocity(fractionalVelocity, angle, );
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

  private CompositeElement createCompositeElementFromGameLoader(){
    List<ViewCollidableRecord> recordList = gameLoaderView.getViewCollidableInfo();
    return new CompositeElement(recordList);
  }

}
