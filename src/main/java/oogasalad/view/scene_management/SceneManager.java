package oogasalad.view.scene_management;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javax.xml.parsers.ParserConfigurationException;
import oogasalad.model.api.GameRecord;
import oogasalad.view.controller.GameController;
import oogasalad.view.visual_elements.CompositeElement;
import oogasalad.view.enums.SceneType;
import org.xml.sax.SAXException;

/**
 * Manages different screens (scenes) within the game, such as the title screen, menu screen, game
 * screen, and transition screen. It updates and transitions between screens based on game state and
 * player interactions.
 *
 * @author Doga Ozmen, Jordan Haytaian
 */
public class SceneManager {
  private final Pane root;
  private final Scene scene;
  private final SceneElementParser sceneElementParser;
  private final SceneElementFactory sceneElementFactory;
  private final SceneElementStyler sceneElementStyler;
  private CompositeElement compositeElement;
  private GameStatusManager gameStatusManager;
  private Pane pauseElements;
  private Pane transitionElements;
  private GameBoard gameBoard;
  private int currentRound;
  private final String titleSceneElementsPath = "data/scene_elements/titleSceneElements.xml";
  private final String menuSceneElementsPath = "data/scene_elements/menuSceneElements.xml";
  private final String gameManagementElementsPath = "data/scene_elements/gameManagementElements.xml";
  private final String transitionElementsPath = "data/scene_elements/transitionElements.xml";
  private final String gameOverSceneElementsPath = "data/scene_elements/gameOverElements.xml";
  private final String pausePath = "data/scene_elements/pauseElements.xml";


  /**
   * Constructor initializes scene, root, sceneElementParser, and sceneElementFactory which are
   * necessary to update scenes with new elements
   *
   * @param gameController handles model/view interactions
   * @param screenWidth    screen width to be used for scaling ratio based elements
   * @param screenHeight   screen height to be used for scaling ratio based elements
   */
  public SceneManager(GameController gameController, double screenWidth,
      double screenHeight) {
    root = new Pane();
    scene = new Scene(root);

    sceneElementParser = new SceneElementParser();
    sceneElementStyler = new SceneElementStyler(root);
    gameStatusManager = new GameStatusManager();
    sceneElementFactory = new SceneElementFactory(screenWidth, screenHeight, sceneElementStyler,
        new SceneElementHandler(gameController, this, gameStatusManager));
  }

  /**
   * Creates a non-game scene by resetting the root and creating new elements from an XML file
   * specified by the type of scene
   *
   * @param sceneType the type of scene to create
   */
  public void createNonGameScene(SceneType sceneType) {
    switch (sceneType) {
      case TITLE -> {
        resetRoot();
        root.getChildren().add(createSceneElements(titleSceneElementsPath));
      }
      case MENU -> {
        resetRoot();
        root.getChildren().add(createSceneElements(menuSceneElementsPath));
      }
      case TRANSITION -> {
        root.getChildren().add(transitionElements);
      }
      case PAUSE -> {
        if (!root.getChildren().contains(pauseElements)) {
          root.getChildren().add(pauseElements);
        }
      }
      case GAME_OVER -> {
        resetRoot();
        root.getChildren().add(createSceneElements(gameOverSceneElementsPath));
      }
    }
  }

  /**
   * Called when game is resumed, removes pause screen elements
   */
  public void removePauseSheen() {
    root.getChildren().remove(pauseElements);
  }

  /**
   * Called when next round is started, removes transition screen elements
   */
  public void removeTransitionSheen() {
    root.getChildren().remove(transitionElements);
    root.requestFocus();
  }

  /**
   * Getter for the scene
   *
   * @return scene displaying game visuals
   */
  public Scene getScene() {
    return scene;
  }

  /**
   * Getter for root node
   *
   * @return root node of scene
   */
  public Pane getRoot() {
    return root;
  }

  /**
   * Updates game elements and stat display from GameRecord info
   *
   * @param gameRecord represents updated state of game
   */
  public void update(GameRecord gameRecord) {
    compositeElement.update(gameRecord.gameObjectRecords());
    gameStatusManager.update(gameRecord.players(), gameRecord.turn(), gameRecord.round());
    root.requestFocus();
    checkEndRound(gameRecord);
  }

  /**
   * Makes the game screen including game board elements, striker input, and game stat display
   *
   * @param compositeElement game board elements
   * @param gameRecord       holds score, turn, and round info for stat display
   */
  public void makeGameScreen(CompositeElement compositeElement, GameRecord gameRecord) {
    this.compositeElement = compositeElement;
    pauseElements = createSceneElements(pausePath);
    transitionElements = createSceneElements(transitionElementsPath);
    addGameManagementElementsToGame(gameRecord);
    addGameElementsToGame();
    root.requestFocus();
  }


  private Pane createSceneElements(String filePath) {
    try {
      List<Map<String, String>> sceneElementParameters = sceneElementParser.getElementParametersFromFile(
          filePath);
      return sceneElementFactory.createSceneElements(sceneElementParameters);

    } catch (ParserConfigurationException | SAXException | IOException e) {
      //TODO: Exception Handling
      return null;
    }
  }

  private void addGameManagementElementsToGame(GameRecord gameRecord) {
    resetRoot();
    currentRound = gameRecord.round();
    Pane sceneElements = createSceneElements(gameManagementElementsPath);
    root.getChildren().addAll(sceneElements);

  }

  private void addGameElementsToGame() {
    gameBoard = new GameBoard(compositeElement);
    root.getChildren().add(gameBoard.getPane());
  }

  private void resetRoot() {
    root.getChildren().clear();
  }

  private void checkEndRound(GameRecord gameRecord) {
    if (gameRecord.gameOver()) {
      createNonGameScene(SceneType.GAME_OVER);
      gameStatusManager.update(gameRecord.players(), gameRecord.turn(), gameRecord.round());
    } else if (gameRecord.round() != currentRound) {
      currentRound = gameRecord.round();
      createNonGameScene(SceneType.TRANSITION);

    }
  }
}
