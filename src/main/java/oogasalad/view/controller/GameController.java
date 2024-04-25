package oogasalad.view.controller;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.ViewGameObjectRecord;
import oogasalad.model.api.exception.InvalidImageException;
import oogasalad.model.api.exception.InvalidShapeException;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameparser.GameLoaderView;
import oogasalad.view.GameWindow;
import oogasalad.view.scene_management.AnimationManager;
import oogasalad.view.scene_management.GameTitleParser;
import oogasalad.view.scene_management.SceneManager;
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
  private final GameTitleParser gameTitleParser;
  private final int maxVelocity;
  private GameEngine gameEngine;
  private GameLoaderView gameLoaderView;
  private boolean ableToStrike;

  /**
   * Initializes the GameController with the specified screen width and height.
   *
   * <p>
   * The constructor creates an instance of `SceneManager` with the provided dimensions and the
   * current GameController as the owner, and then initializes the title scene. Additionally, an
   * `AnimationManager` instance is created to handle game animations, and the controller is set to
   * be able to strike by default. The max velocity is initialized.
   * </p>
   *
   * @param width  The width of the screen for the game.
   * @param height The height of the screen for the game.
   */
  public GameController(double width, double height) {
    sceneManager = new SceneManager(this, width, height);
    animationManager = new AnimationManager();
    gameTitleParser = new GameTitleParser();
    ableToStrike = true;
    maxVelocity = 1000;
  }

  /**
   * Sets the scene to the title scene by prompting the scene manager to create it
   *
   * @return title scene
   */
  public Scene setSceneToTitle() {
    sceneManager.createTitleScene();
    return sceneManager.getScene();
  }

  /**
   * Sets the scene to the menu scene by prompting the scene manager to create it
   *
   * @return menu scene
   */
  public Scene setSceneToMenu() {
    sceneManager.createMenuScene();
    return sceneManager.getScene();
  }

  /**
   * Pauses the game by switching to a pause scene and pausing all animations.
   *
   * <p>
   * This method triggers the transition to a pause scene, allowing the player to take a break or
   * adjust settings. It also pauses all ongoing animations to halt the game's progression during
   * the pause.
   * </p>
   */
  public void pauseGame() {
    animationManager.pauseAnimation();
  }

  /**
   * Resumes the game by removing pause sheen elements and resuming all animations.
   *
   * <p>
   * This method removes the pause sheen and transitions back to the previous scene, allowing the
   * game to continue as it was before the pause. It also resumes any animations that were paused.
   * </p>
   */
  public void resumeGame() {
    sceneManager.removePauseSheen();
    animationManager.resumeAnimation();
  }

  /**
   * Opens the authoring environment for creating game content.
   *
   * <p>
   * This method creates a new instance of the `NewAuthoringController` and uses it to update the
   * authoring screen. This allows the user to access the authoring environment, where they can
   * create game content as needed.
   * </p>
   */
  public void openAuthorEnvironment() {
    AuthoringController newAuthoringController = new AuthoringController();
    newAuthoringController.updateAuthoringScreen();
  }

  /**
   * Starts the selected game by loading necessary back end components, creating the scene, and
   * starting the animation
   *
   * @param selectedGame the game title selected to play
   */
  public void startGamePlay(String selectedGame) {
    gameLoaderView = new GameLoaderView(selectedGame);
    gameEngine = new GameEngine(selectedGame);
    GameRecord gameRecord = gameEngine.restoreLastStaticGameRecord();
    CompositeElement compositeElement = createCompositeElementFromGameLoader();
    sceneManager.makeGameScreen(compositeElement, gameRecord);
    sceneManager.update(gameRecord);
  }

  /**
   * Sends velocity and angle to back end to simulate hitting point scoring object
   *
   * @param fractionalVelocity velocity as fraction of maxVelocity
   */
  public void hitPointScoringObject(double fractionalVelocity, double angle) {
    if (ableToStrike) {
      gameEngine.applyInitialVelocity(maxVelocity * fractionalVelocity, angle);
      ableToStrike = false;
      animationManager.runAnimation(this);
    }
  }

  /**
   * Updates the game state based on the provided time step and checks if the game is in a static
   * state.
   *
   * <p>
   * This method advances the game state by the given time step and evaluates whether all elements
   * in the game are stationary. It then updates the scene manager with the current game record and
   * gets the current strikeable element. If the game is in a static state (all elements are
   * stationary), the ability to strike is set to true.
   * </p>
   *
   * @param timeStep The time step for updating the animation and game state.
   * @return {@code true} if the game is in a static state (all elements are stationary), otherwise
   * {@code false}.
   */
  public boolean runGameAndCheckStatic(double timeStep) {
    GameRecord gameRecord = gameEngine.update(timeStep);
    boolean staticState = gameRecord.staticState();
    if (staticState) {
      ableToStrike = true;
    }
    sceneManager.update(gameRecord);
    return staticState;
  }

  /**
   * Prompts the GameTitleParser to parse for the playable game titles
   *
   * @return a list of the playable game titles
   */
  public ObservableList<String> getGameTitles() {
    return gameTitleParser.getGameTitles();
  }

  /**
   * Creates a new game window for the user to play or author a games
   */
  public void createNewWindow() {
    new GameWindow();
  }

  private CompositeElement createCompositeElementFromGameLoader() {
    try {
      List<ViewGameObjectRecord> recordList = gameLoaderView.getViewCollidableInfo();
      return new CompositeElement(recordList);
    } catch (InvalidShapeException | InvalidImageException e) {
      LOGGER.error(e.getMessage());
      return null;
    }
  }

  public void moveX(boolean positive) {
    if (animationManager.isRunning()) {
      gameEngine.moveActiveControllableX(positive);
    }
  }

  public void moveY(boolean positive) {
    if (animationManager.isRunning()) {
      gameEngine.moveActiveControllableY(positive);
    }
  }
}
