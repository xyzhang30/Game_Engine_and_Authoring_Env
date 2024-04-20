package oogasalad.view.game_environment.non_game_environment_scene;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javax.xml.parsers.ParserConfigurationException;
import oogasalad.model.api.GameRecord;
import oogasalad.view.controller.GameController;
import oogasalad.view.game_environment.GameScreen;
import oogasalad.view.game_environment.GameplayPanel.GamePanel;
import oogasalad.view.game_environment.GameplayPanel.TransformableNode;
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

  private final ReadOnlyDoubleProperty screenWidthObserver;
  private final ReadOnlyDoubleProperty screenHeightObserver;
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
  //private final String gameStatElementsPath = "data/scene_elements/gameStatElements.xml";


  public SceneManager(GameController gameController, ReadOnlyDoubleProperty screenWidthObserver,
      ReadOnlyDoubleProperty screenHeightObserver) {

    this.screenWidthObserver = screenWidthObserver;
    this.screenHeightObserver = screenHeightObserver;

    root = new Pane();
    scene = new Scene(root);

    sceneElementParser = new SceneElementParser();
    sceneElementFactory = new SceneElementFactory(root, 1000, 1000,
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
      Pane sceneElements = sceneElementFactory.createSceneElements(sceneElementParameters);

      TransformableNode transformableNode = new TransformableNode(sceneElements);
      transformableNode.sizeToBounds(screenWidthObserver.get(), screenHeightObserver.get());
      screenWidthObserver.addListener((observable, oldValue, newValue) -> {
        transformableNode.sizeToBounds(newValue.doubleValue(), screenHeightObserver.get());
      });
      screenHeightObserver.addListener((observable, oldValue, newValue) -> {
        transformableNode.sizeToBounds(screenWidthObserver.get(), newValue.doubleValue());
      });

      return transformableNode.getPane();

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
  }

  public void makeGameScreen(GameController controller, CompositeElement compositeElement,
      GameRecord gameRecord) {
    this.compositeElement = compositeElement;
    pauseElements = createSceneElements(pausePath);
    addGameManagementElementsToGame(gameRecord);
    addGameElementsToGame();
  }

  private void addGameManagementElementsToGame(GameRecord gameRecord) {
    resetRoot();
    Pane sceneElements = createSceneElements(gameManagementElementsPath);
    gameStatBoard = new GameStatBoard(gameRecord.players(), gameRecord.turn(), gameRecord.round());
    root.getChildren().addAll(sceneElements, gameStatBoard.getContainer());

  }

  private void addGameElementsToGame() {
    gamePanel = new GamePanel(compositeElement);
    root.getChildren().add(gamePanel.getPane());
  }
  //refactor methods below here

  public void enableHitting() {
    gameScreen.enableHitting();
  }

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
