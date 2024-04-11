package oogasalad.view.Controlling;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.Pair;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.ViewCollidableRecord;
import oogasalad.model.api.exception.InvalidImageException;
import oogasalad.model.api.exception.InvalidShapeException;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameparser.GameLoaderView;
import oogasalad.view.AnimationManager;
import oogasalad.view.CollisionManager;
import oogasalad.view.SceneManager;
import oogasalad.view.VisualElements.CompositeElement;
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
  private final CollisionManager collisionManager;
  private final SceneManager sceneManager;
  private final AnimationManager animationManager;
  private int controllableID;
  private GameEngine gameEngine;
  private GameLoaderView gameLoaderView;
  private final String PLAYABLE_GAMES_DIRECTORY = "data/playable_games";
  private final String TEST_FILE_IDENTIFIER = "test";

  public GameController() {
    sceneManager = new SceneManager();
    sceneManager.makeTitleScreen(this);
    animationManager = new AnimationManager();
    collisionManager = new CollisionManager();
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
   // sceneManager.updateScoreBoard(gameRecord.players().get(0).score());

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
    gameEngine.applyInitialVelocity(700 * fractionalVelocity, angle,
        controllableID); // The 8 has been hard
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
      List<ViewCollidableRecord> recordList = gameLoaderView.getViewCollidableInfo();
      controllableID = gameLoaderView.getControllableIds().controllableIds().get(0);
      return new CompositeElement(recordList);
    } catch (InvalidShapeException | InvalidImageException e) {
      System.out.println(e.getMessage());
      LOGGER.error(e.getMessage());
      return null;
    }
  }

}
