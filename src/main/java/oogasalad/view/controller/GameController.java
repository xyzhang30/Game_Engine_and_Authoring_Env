package oogasalad.view.controller;

import java.util.List;
import javafx.scene.Scene;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.api.ViewGameObjectRecord;
import oogasalad.model.api.exception.InvalidImageException;
import oogasalad.model.api.exception.InvalidShapeException;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameparser.GameLoaderView;
import oogasalad.view.authoring_environment.NewAuthoringController;
import oogasalad.view.game_environment.AnimationManager;
import oogasalad.view.game_environment.non_game_environment_scene.SceneManager;
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
  private int strikeableID;
  private int activePlayer;
  private GameEngine gameEngine;
  private GameLoaderView gameLoaderView;


  public GameController(double width, double height) {
    sceneManager = new SceneManager(this, width, height);
    sceneManager.createNonGameScene(SceneType.TITLE);
    animationManager = new AnimationManager();
  }

  public Scene getScene() {
    return sceneManager.getScene();
  }

  public void pauseGame() {
    sceneManager.createNonGameScene(SceneType.PAUSE);
    animationManager.pauseAnimation();
  }

  public void resumeGame() {
    sceneManager.removePauseSheen();
    animationManager.resumeAnimation();
  }

  public void openTransitionScreen() {
    //sceneManager.makeTransitionScreen();
  }

  public void openAuthorEnvironment() {
    NewAuthoringController newAuthoringController = new NewAuthoringController();
    newAuthoringController.updateAuthoringScreen();
//    AuthoringController authoringController = new AuthoringController();
//    authoringController.startAuthoring();
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
    GameRecord gameRecord = gameEngine.restoreLastStaticGameRecord();
    getCurrentStrikeable(gameRecord);
    CompositeElement compositeElement = createCompositeElementFromGameLoader();
    sceneManager.makeGameScreen(this, compositeElement, gameRecord);
    sceneManager.update(gameRecord);
  }

  private void getCurrentStrikeable(GameRecord gameRecord) {
    activePlayer = gameRecord.turn();
    for (PlayerRecord p : gameRecord.players()) {
      if (p.playerId() == activePlayer) {
        strikeableID = p.activeStrikeable();
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
    getCurrentStrikeable(gameRecord);
    sceneManager.update(gameRecord);
    return staticState;
  }

  /**
   * /** Sends velocity and angle to back end to simulate hitting point scoring object
   *
   * @param fractionalVelocity velocity as fraction of maxVelocity
   */
  public void hitPointScoringObject(double fractionalVelocity, double angle) {
    gameEngine.applyInitialVelocity(700 * fractionalVelocity, angle,
        strikeableID); // The 8 has been hard
    // coded!

    animationManager.runAnimation(this);
  }

  private CompositeElement createCompositeElementFromGameLoader() {
    try {
      List<ViewGameObjectRecord> recordList = gameLoaderView.getViewCollidableInfo();
      return new CompositeElement(recordList);
    } catch (InvalidShapeException | InvalidImageException e) {
      System.out.println(e.getMessage());
      LOGGER.error(e.getMessage());
      return null;
    }
  }
}
