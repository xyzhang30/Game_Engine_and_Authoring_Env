package oogasalad.view.game_environment.scene_management;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javax.xml.parsers.ParserConfigurationException;
import oogasalad.model.api.GameRecord;
import oogasalad.view.controller.GameController;
import oogasalad.view.game_environment.GameScreen;
import oogasalad.view.game_environment.GamePanel;
import oogasalad.view.visual_elements.CompositeElement;
import oogasalad.view.enums.SceneType;
import org.xml.sax.SAXException;

/**
 * Manages different screens (scenes) within the game, such as the title screen, menu screen, game
 * screen, and transition screen. It updates and transitions between screens based on game state and
 * player interactions.
 *
 * @author Doga Ozmen
 */
public class SceneManager {

  private final double screenWidth;
  private final double screenHeight;
  private final Pane root;
  private final Scene scene;
  private final SceneElementParser sceneElementParser;
  private final SceneElementFactory sceneElementFactory;
  private CompositeElement compositeElement;
  private GameStatBoard gameStatBoard;
  private GameScreen gameScreen;
  private GamePanel gamePanel;
  private Pane pauseElements;
  private int currentRound = 1;
  private final String titleSceneElementsPath = "data/scene_elements/titleSceneElements.xml";
  private final String menuSceneElementsPath = "data/scene_elements/menuSceneElements.xml";
  private final String gameManagementElementsPath = "data/scene_elements/gameManagementElements.xml";
  private final String pausePath = "data/scene_elements/pauseElements.xml";


  public SceneManager(GameController gameController, double screenWidth,
      double screenHeight) {
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    root = new Pane();
    scene = new Scene(root);

    sceneElementParser = new SceneElementParser();
    sceneElementFactory = new SceneElementFactory(root, screenWidth, screenHeight,
        new SceneElementHandler(gameController, this));
  }

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
      }
      case PAUSE -> {
        //TODO: Make pause sheen the size of the gameboard
        if (!root.getChildren().contains(pauseElements)) {
          root.getChildren().add(pauseElements);
        }
      }
    }
  }

  public void removePauseSheen() {
    root.getChildren().remove(pauseElements);
  }

  public void panelZoomIn() {
    gamePanel.zoomIn();
  }

  public void panelZoomOut() {
    gamePanel.zoomOut();
  }

  public void panelZoomReset() {
    //TODO: write this
  }

  public Pane createSceneElements(String filePath) {
    try {
      List<Map<String, String>> sceneElementParameters = sceneElementParser.getElementParametersFromFile(
          filePath);
      return sceneElementFactory.createSceneElements(sceneElementParameters);

    } catch (ParserConfigurationException | SAXException | IOException e) {
      //TODO: Exception Handling
      return null;
    }
  }

  public Scene getScene() {
    return scene;
  }

  public void update(GameRecord gameRecord) {
    compositeElement.update(gameRecord.gameObjectRecords());
    gameStatBoard.update(gameRecord.players(), gameRecord.turn(), gameRecord.round());
    checkEndRound(gameRecord);
    root.requestFocus();
  }

  public void makeGameScreen(GameController controller, CompositeElement compositeElement,
      GameRecord gameRecord) {
    this.compositeElement = compositeElement;
    pauseElements = createSceneElements(pausePath);
    addGameManagementElementsToGame(gameRecord);
    addGameElementsToGame();
    root.requestFocus();
  }

  private void addGameManagementElementsToGame(GameRecord gameRecord) {
    resetRoot();
    Pane sceneElements = createSceneElements(gameManagementElementsPath);
    gameStatBoard = new GameStatBoard(gameRecord.players(), gameRecord.turn(), gameRecord.round(),
        screenWidth, screenHeight);
    root.getChildren().addAll(sceneElements, gameStatBoard.getContainer());

  }

  private void addGameElementsToGame() {
    gamePanel = new GamePanel(compositeElement);
    root.getChildren().add(gamePanel.getPane());
  }
  //refactor methods below here

  public void checkEndRound(GameRecord gameRecord) {
    if (gameRecord.round() != currentRound) {
      currentRound = gameRecord.round();
    }
    if (gameRecord.gameOver()) {
      gameScreen.endRound(true);
    }
  }

  private void resetRoot() {
    root.getChildren().clear();
  }
}
