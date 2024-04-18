package oogasalad.view.game_environment.non_game_environment_scene;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javax.xml.parsers.ParserConfigurationException;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;
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
  private final SceneElementHandler sceneElementHandler;
  private CompositeElement compositeElement;
  private GameScreen gameScreen;
  private int currentRound = 1;
  private final String titleSceneElementsPath = "data/scene_elements/titleSceneElements.xml";
  private final String menuSceneElementsPath = "data/scene_elements/menuSceneElements.xml";
  private final String gameManagementElementsPath = "data/scene_elements/gameManagementElements.xml";
  private final String gameStatElementsPath = "data/scene_elements/gameStatElements.xml";


  public SceneManager(GameController control, ReadOnlyDoubleProperty w, ReadOnlyDoubleProperty h) {
    root = new Pane();
    scene = new Scene(root);
    sceneElementParser = new SceneElementParser();
    sceneElementHandler = new SceneElementHandler(control);
    sceneElementFactory = new SceneElementFactory(root, 1000, 1000,
        sceneElementHandler);

    screenWidthObserver = w;
    screenHeightObserver = h;
  }

  public void createNonGameScene(SceneType sceneType) {
    resetRoot();
    switch (sceneType) {
      case TITLE -> {
        createSceneElementsAndUpdateRoot(titleSceneElementsPath);
      }
      case MENU -> {
        createSceneElementsAndUpdateRoot(menuSceneElementsPath);
      }
      case TRANSITION -> {
      }
      case PAUSE -> {
      }
    }
  }

  public void createSceneElementsAndUpdateRoot(String filePath) {
    try {
      List<Map<String, String>> sceneElementParameters = sceneElementParser.getElementParametersFromFile(
          filePath);
      Pane sceneElements = sceneElementFactory.createSceneElements(sceneElementParameters);

      TransformableNode tf = new TransformableNode(sceneElements);
      tf.sizeToBounds(screenWidthObserver.get(), screenHeightObserver.get());
      screenWidthObserver.addListener((observable, oldValue, newValue)->{
        tf.sizeToBounds(newValue.doubleValue(), screenHeightObserver.get());
      });
      screenHeightObserver.addListener((observable, oldValue, newValue) -> {
        tf.sizeToBounds(screenWidthObserver.get(), newValue.doubleValue());
      });

      root.getChildren().setAll(tf.getPane());

    } catch (ParserConfigurationException e) {
      //TODO: Exception Handling
    } catch (SAXException e) {
      //TODO: Exception Handling
    } catch (IOException e) {
      //TODO: Exception Handling
    }
  }

  public Scene getScene() {
    return scene;
  }

  public void update(GameRecord gameRecord) {
    compositeElement.update(gameRecord.gameObjectRecords());
    Map<Integer, Double> scoreMap = new TreeMap<>();
    for (PlayerRecord p : gameRecord.players()) {
      scoreMap.put(p.playerId(), p.score());
    }
    updateScoreTurnBoard(scoreMap, gameRecord.turn(), gameRecord.round());
    checkEndRound(gameRecord);
  }

  public void makeGameScreen(GameController controller, CompositeElement compositeElement) {
    this.compositeElement = compositeElement;
    addNonGameElementsToGame();
    addGameElementsToGame();

//    gameScreen = new GameScreen(controller, compositeElement);
//    scene.setRoot(gameScreen.getRoot());
//    gameScreen.initiateListening(scene);
  }

  private void addNonGameElementsToGame(){
    resetRoot();
    createSceneElementsAndUpdateRoot(gameManagementElementsPath);
    createSceneElementsAndUpdateRoot(gameStatElementsPath);
  }

  private void addGameElementsToGame(){
    GamePanel gamePanel = new GamePanel(compositeElement);
    root.getChildren().add(gamePanel.getPane());
  }

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

  public void updateScoreTurnBoard(Map<Integer, Double> scoreMap, int turn, int round) {
    gameScreen.updateScoreBoard(scoreMap);
    gameScreen.updateTurnBoard(turn, round);
  }

  private void resetRoot() {
    root.getChildren().clear();
  }
}
