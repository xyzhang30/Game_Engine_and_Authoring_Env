package oogasalad.view.scene_management;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.view.controller.GameController;
import oogasalad.view.api.enums.SceneElementEventType;
import oogasalad.view.api.enums.SceneType;

/**
 * Handles scene element events and interactions within a game environment.
 * <p>
 * The `SceneElementHandler` class is responsible for managing the behavior of various elements
 * within a game scene. It interacts with the game controller and scene manager to facilitate
 * different types of events such as scene changes, gameplay changes, and striking actions. The
 * class uses the event type specified in the event parameter to determine the appropriate handler
 * method to call for the provided scene element.
 * <p>
 * The class allows for the creation of different event handlers for pause, resume, save, load, and
 * game start events. It also includes methods to handle power and angle adjustments for striking
 * events, as well as key event handlers to modify the power and angle based on user input.
 *
 * @author Jordan Haytaian
 */
public class SceneElementHandler {

  private final GameController gameController;
  private final SceneManager sceneManager;
  private final GameStatusManager gameStatusManager;
  private final int angleIncrement;
  private double maxPower;
  private double minPower;
  private Rectangle powerMeter;
  private Polygon angleArrow;

  /**
   * Constructs a new instance of the SceneElementHandler class.
   * <p>
   * This constructor initializes a SceneElementHandler object with the specified game controller
   * and scene manager. The game controller is responsible for managing the game state, while the
   * scene manager is responsible for managing different scenes within the game environment.
   * Additionally, the constructor sets the angle increment value to a default of 5 degrees.
   *
   * @param gameController    An instance of the `GameController` class, responsible for managing
   *                          the game state.
   * @param sceneManager      An instance of the `SceneManager` class, responsible for managing
   *                          different scenes within the game environment.
   * @param gameStatusManager An instance of the 'GameStatusManager' class, responsible for managing
   *                          the game elements related to displaying the game status
   */
  public SceneElementHandler(GameController gameController, SceneManager sceneManager,
      GameStatusManager gameStatusManager) {
    this.gameController = gameController;
    this.sceneManager = sceneManager;
    this.gameStatusManager = gameStatusManager;
    angleIncrement = 5;
  }

  /**
   * Handles events for the specified node based on the given event type.
   * <p>
   * This method checks the event type of the given node and delegates the handling of the event to
   * the appropriate method. It checks for different types of events such as scene change events,
   * gameplay change events, and striking events, and calls the corresponding handler method for
   * each event type.
   *
   * @param node  The scene element node to handle events for.
   * @param event A string representing the type of event to handle.
   */
  public void createElementHandler(Node node, String event) {
    checkForSceneChangeEvent(node, event);
    checkForGamePlayChangeEvent(node, event);
    checkForStrikingEvent(node, event);
    checkForGameManagementEvent(node, event);
  }

  private void checkForGamePlayChangeEvent(Node node, String event) {
    switch (SceneElementEventType.valueOf(event)) {
      case PAUSE -> {
        createPauseHandler(node);
      }
      case RESUME -> {
        createResumeHandler(node);
      }
      case SAVE -> {
        createSaveHandler(node);
      }
      case LOAD -> {
        createLoadHandler(node);
      }
    }
  }

  private void checkForSceneChangeEvent(Node node, String event) {
    switch (SceneElementEventType.valueOf(event)) {
      case START_TITLE -> {
        createStartTitleHandler(node);
      }
      case START_MENU -> {
        createStartMenuHandler(node);
      }
      case START_AUTHORING -> {
        createStartAuthoringHandler(node);
      }
      case START_GAME -> {
        createStartGameHandler(node);
      }
      case NEXT_ROUND -> {
        createNextRoundHandler(node);
      }
    }

  }

  private void checkForStrikingEvent(Node node, String event) {
    switch (SceneElementEventType.valueOf(event)) {
      case POWER_HEIGHT -> {
        getMaxPower(node);
      }
      case SET_POWER -> {
        getMinPower(node);
        createPowerHandler(node);
      }
      case SET_ANGLE -> {
        setAngleArrow(node);
      }
    }
  }

  private void checkForGameManagementEvent(Node node, String event) {
    switch (SceneElementEventType.valueOf(event)) {
      case SET_ROUND -> {
        setRound(node);
      }
      case SET_TURN -> {
        setTurn(node);
      }
      case SET_SCORE -> {
        setScores(node);
      }
    }
  }

  private void createSaveHandler(Node node) {
    node.setOnMouseClicked((e -> {
      sceneManager.getRoot().requestFocus();
    }));
  }

  private void createLoadHandler(Node node) {
    node.setOnMouseClicked((e -> {
      sceneManager.getRoot().requestFocus();
    }));
  }

  private void createStartMenuHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.createNonGameScene(SceneType.MENU));
  }

  private void createStartAuthoringHandler(Node node) {
    node.setOnMouseClicked(e -> gameController.openAuthorEnvironment());
  }

  private void createStartGameHandler(Node node) {
    ListView<String> gameList = (ListView<String>) node;
    gameList.setItems(gameController.getGameTitles());
    node.setOnMouseClicked(e -> {
      String game = gameList.getSelectionModel().getSelectedItem();
      if (game != null) {
        gameController.startGamePlay(game);
      }
    });
  }

  private void createPauseHandler(Node node) {
    node.setOnMouseClicked((e -> {
      gameController.pauseGame();
      sceneManager.getRoot().requestFocus();
    }));
  }

  private void createResumeHandler(Node node) {
    node.setOnMouseClicked(e -> gameController.resumeGame());
  }

  private void getMaxPower(Node node) {
    maxPower = ((Rectangle) node).getHeight();
  }

  private void getMinPower(Node node) {
    minPower = ((Rectangle) node).getHeight();
  }

  private void createPowerHandler(Node node) {
    powerMeter = (Rectangle) node;
    Pane root = sceneManager.getRoot();
    root.setOnKeyPressed(e -> {
      switch (e.getCode()) {
        case UP: {
          increasePower();
          break;
        }
        case DOWN: {
          decreasePower();
          break;
        }
        case LEFT: {
          decreaseAngle();
          break;
        }
        case RIGHT: {
          increaseAngle();
          break;
        }
        case ENTER: {
          handleStrike();
          break;
        }
        case M: {
          gameController.moveX(true);
          break;
        }
        case N: {
          gameController.moveX(false);
          break;
        }
        case B: {
          gameController.moveY(true);
          break;
        }
        case V: {
          gameController.moveY(false);
          break;
        }
      }
    });
  }

  private void decreaseAngle() {
    if (angleArrow.getRotate() > -180) {
      angleArrow.setRotate(angleArrow.getRotate() - angleIncrement);
    }
  }

  private void increaseAngle() {
    if (angleArrow.getRotate() < 180) {
      angleArrow.setRotate(angleArrow.getRotate() + angleIncrement);
    }
  }

  private void increasePower() {
    if (powerMeter.getHeight() < maxPower - 3 * minPower) {
      powerMeter.setLayoutY(powerMeter.getLayoutY() - 10);
      powerMeter.setHeight(powerMeter.getHeight() + 10);
    }
  }

  private void decreasePower() {
    if (powerMeter.getHeight() > 2 * minPower) {
      powerMeter.setLayoutY(powerMeter.getLayoutY() + 10);
      powerMeter.setHeight(powerMeter.getHeight() - 10);
    }
  }

  private void handleStrike() {
    double angle = (-90 + angleArrow.getRotate()) * (Math.PI / 180);
    double fractionalVelocity = powerMeter.getHeight() / maxPower;
    gameController.hitPointScoringObject(fractionalVelocity, angle);
  }

  private void setAngleArrow(Node node) {
    angleArrow = (Polygon) node;
    System.out.println(angleArrow.getRotate());
  }

  private void createNextRoundHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.removeTransitionSheen());
  }

  private void createStartTitleHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.createNonGameScene(SceneType.TITLE));
  }

  private void setRound(Node node) {
    gameStatusManager.setRoundText(((Text) node));
  }

  private void setTurn(Node node) {
    gameStatusManager.setTurnText(((Text) node));
  }

  private void setScores(Node node) {
    gameStatusManager.setScoreList((ListView<String>) node);
    node.setOnMouseClicked(e -> sceneManager.getRoot().requestFocus());
  }
}
