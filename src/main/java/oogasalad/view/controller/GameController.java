package oogasalad.view.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.Scene;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.api.ViewGameObjectRecord;
import oogasalad.model.api.exception.InvalidImageException;
import oogasalad.model.api.exception.InvalidShapeException;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameparser.GameLoaderView;
import oogasalad.view.AnimationManager;
import oogasalad.view.SceneManager;
import oogasalad.view.authoring_environment.NewAuthoringController;
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
  private final String PLAYABLE_GAMES_DIRECTORY = "data/playable_games";
  private final String TEST_FILE_IDENTIFIER = "test";
  private int strikeableID;
  private int activePlayer;
  private GameEngine gameEngine;
  private GameLoaderView gameLoaderView;

  public GameController() {
    sceneManager = new SceneManager();
    sceneManager.makeTitleScreen(this);
    animationManager = new AnimationManager();
  }

  public Scene getScene() {
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
    getCurrentStrikeable(gameEngine.restoreLastStaticGameRecord());
    CompositeElement compositeElement = createCompositeElementFromGameLoader();
    sceneManager.makeGameScreen(this, compositeElement);
    sceneManager.update(gameEngine.restoreLastStaticGameRecord());
  }

  private void getCurrentStrikeable(GameRecord gameRecord) {
    activePlayer = gameRecord.turn();
    for (PlayerRecord p : gameRecord.players()) {
      if (p.playerId() == activePlayer) {
        strikeableID = p.activeStrikeable();
        System.out.println(strikeableID);
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
      for (GameObjectRecord r : gameRecord.gameObjectRecords()) {
        if (List.of(9, 14, 15, 16).contains(r.id())) {
          System.out.println(r);
        }
      }
      System.out.println();
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


  /**
   * Gets the titles of all available games to play
   *
   * @return a list of the game titles
   */
  public List<String> getGameTitles() {
    Set<String> files = listFiles(PLAYABLE_GAMES_DIRECTORY);
    List<String> gameTitles = new ArrayList<>();
    for (String file : files) {
      if (!file.toLowerCase().contains(TEST_FILE_IDENTIFIER)) {
        gameTitles.add(file.substring(0, file.indexOf(".")));
      }
    }
    return gameTitles;
  }

  private Set<String> listFiles(String dir) {
    return Stream.of(new File(dir).listFiles())
        .filter(file -> !file.isDirectory())
        .map(File::getName)
        .collect(Collectors.toSet());
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
