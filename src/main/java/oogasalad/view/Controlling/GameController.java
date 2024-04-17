package oogasalad.view.Controlling;

import java.util.List;
import javafx.scene.Scene;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.api.ViewCollidableRecord;
import oogasalad.model.api.exception.InvalidImageException;
import oogasalad.model.api.exception.InvalidShapeException;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameparser.GameLoaderView;
import oogasalad.view.playing_scene.AnimationManager;
import oogasalad.view.playing_scene.SceneManager;
import oogasalad.view.enums.SceneType;
import oogasalad.view.visual_elements.CompositeElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * GameController class handles communications between model and view.  This class holds manager
 * class instances to delegate handling the information received from the model.
 *
 * @author Jordan Haytaian
 */
public class GameController {

  private static final Logger LOGGER = LogManager.getLogger(GameEngine.class);
  private final SceneManager sceneManager;
  private final AnimationManager animationManager;
  private int controllableID;
  private int activePlayer;
  private GameEngine gameEngine;
  private GameLoaderView gameLoaderView;

  public GameController() {
    sceneManager = new SceneManager();
    sceneManager.createScene(SceneType.MENU);
    animationManager = new AnimationManager();
  }

  public Scene getScene() {
    return sceneManager.getScene();
  }

  /**
   * Creates and displays menu screen
   */
  public void openMenuScreen() {
    //sceneManager.makeMenuScreen(getGameTitles(), this);
  }

  public void openTransitionScreen() {
    //sceneManager.makeTransitionScreen();
  }

  public void openAuthorEnvironment() {
    AuthoringController authoringController = new AuthoringController();
    authoringController.startAuthoring();
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
    getCurrentControllable(gameEngine.getGameRecord());
    CompositeElement compositeElement = createCompositeElementFromGameLoader();
    sceneManager.makeGameScreen(this, compositeElement);
    sceneManager.update(gameEngine.getGameRecord());
  }

  private void getCurrentControllable(GameRecord gameRecord) {
    activePlayer = gameRecord.turn();
    for(PlayerRecord p : gameRecord.players()) {
      if(p.playerId()==activePlayer) {
        controllableID = p.activeControllable();
        System.out.println(controllableID);
        break;
      }
    }
  }


  /**
   * Method to update visual game elements
   *
   * @param timeStep timestep for animation
   * @return boolean indicating if round is over
   */
  public boolean runGameAndCheckStatic(double timeStep) {
    GameRecord gameRecord = gameEngine.update(timeStep);
    boolean staticState = gameRecord.staticState();
    if (staticState) {
      sceneManager.enableHitting();
    }
    getCurrentControllable(gameRecord);
    sceneManager.update(gameRecord);
    return staticState;
  }

  /**


   /**
   * Sends velocity and angle to back end to simulate hitting point scoring object
   *
   * @param fractionalVelocity velocity as fraction of maxVelocity
   */
  public void hitPointScoringObject(double fractionalVelocity, double angle) {
    gameEngine.applyInitialVelocity(700 * fractionalVelocity, angle,
        controllableID); // The 8 has been hard
    // coded!

    animationManager.runAnimation(this);
  }

  private CompositeElement createCompositeElementFromGameLoader() {
    try {
      List<ViewCollidableRecord> recordList = gameLoaderView.getViewCollidableInfo();
      return new CompositeElement(recordList);
    } catch (InvalidShapeException | InvalidImageException e) {
      System.out.println(e.getMessage());
      LOGGER.error(e.getMessage());
      return null;
    }
  }

}
