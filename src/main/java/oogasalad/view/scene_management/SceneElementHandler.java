package oogasalad.view.scene_management;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.view.controller.GameController;
import oogasalad.view.api.enums.SceneElementEventType;
import oogasalad.view.enums.ThemeType;

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
  private Map<SceneElementEventType, Consumer<Node>> eventMap;
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
    createEventMap();
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
    Consumer<Node> consumer = eventMap.get(SceneElementEventType.valueOf(event));
    consumer.accept(node);
  }

  private void createEventMap() {
    eventMap = new HashMap<>();
    addSceneChangeEventsToMap();
    addGamePlayChangeEventsToMap();
    addStrikingEventsToMap();
    addGameManagementEventsToMap();
    addDatabaseEventsToMap();
  }

  private void addGamePlayChangeEventsToMap() {
    eventMap.put(SceneElementEventType.PAUSE, this::createPauseHandler);
    eventMap.put(SceneElementEventType.RESUME, this::createResumeHandler);
    eventMap.put(SceneElementEventType.SAVE, this::createSaveHandler);
    eventMap.put(SceneElementEventType.LOAD, this::createLoadHandler);
  }

  private void addSceneChangeEventsToMap() {
    eventMap.put(SceneElementEventType.START_TITLE, this::createStartTitleHandler);
    eventMap.put(SceneElementEventType.START_MENU, this::createStartMenuHandler);
    eventMap.put(SceneElementEventType.START_AUTHORING, this::createStartAuthoringHandler);
    eventMap.put(SceneElementEventType.START_GAME, this::createStartGameHandler);
    eventMap.put(SceneElementEventType.NEXT_ROUND, this::createNextRoundHandler);
    eventMap.put(SceneElementEventType.NEW_GAME_WINDOW, this::createNewGameHandler);
    eventMap.put(SceneElementEventType.CHANGE_THEME, this::createThemeChangeHandler);
  }

  private void addDatabaseEventsToMap(){
    eventMap.put(SceneElementEventType.LOGIN, this::createLoginHandler);
    eventMap.put(SceneElementEventType.CREATE_USER, this::createUserCreatorHandler);
    eventMap.put(SceneElementEventType.USER_TEXT, this::createUsernameHandler);
    eventMap.put(SceneElementEventType.PASSWORD_TEXT, this::createPasswordHandler);
  }


  private void addStrikingEventsToMap() {
    eventMap.put(SceneElementEventType.POWER_HEIGHT, this::getMaxPower);
    eventMap.put(SceneElementEventType.SET_POWER, this::createPowerHandler);
    eventMap.put(SceneElementEventType.SET_ANGLE, this::setAngleArrow);
  }

  private void addGameManagementEventsToMap() {
    eventMap.put(SceneElementEventType.SET_ROUND, this::setRound);
    eventMap.put(SceneElementEventType.SET_TURN, this::setTurn);
    eventMap.put(SceneElementEventType.SET_SCORE, this::setScores);
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
    node.setOnMouseClicked(e -> sceneManager.createMenuScene());
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
      sceneManager.createPauseDisplay();
      gameController.pauseGame();
      sceneManager.getRoot().requestFocus();
    }));
  }

  private void createResumeHandler(Node node) {
    node.setOnMouseClicked(e -> {
      sceneManager.removePauseSheen();
      sceneManager.getRoot().requestFocus();
      gameController.resumeGame();
    });
  }

  private void getMaxPower(Node node) {
    maxPower = ((Rectangle) node).getHeight();
  }

  private void createPowerHandler(Node node) {
    powerMeter = (Rectangle) node;
    minPower = powerMeter.getHeight();
    boolean ableToStrike = gameController.getAbleToStrike();
    Pane root = sceneManager.getRoot();
    root.setOnKeyPressed(e -> {
      switch (e.getCode()) {
        case UP: {
          handleUpKey(ableToStrike);
          break;
        }
        case DOWN: {
          handleDownKey(ableToStrike);
          break;
        }
        case LEFT: {
          handleLeftKey(ableToStrike);
          break;
        }
        case RIGHT: {
          handleRightKey(ableToStrike);
          break;
        }
        case ENTER: {
          handleStrike(ableToStrike);
          break;
        }
      }
    });
  }

  private void handleUpKey(boolean ableToStrike) {
    if (ableToStrike) {
      increasePower();
    } else {
      gameController.moveControllableY(true);
    }
  }

  private void handleDownKey(boolean ableToStrike) {
    if (ableToStrike) {
      decreasePower();
    } else {
      gameController.moveControllableY(false);
    }
  }

  private void handleLeftKey(boolean ableToStrike) {
    if (ableToStrike) {
      decreaseAngle();
    } else {
      gameController.moveControllableX(false);
    }
  }

  private void handleRightKey(boolean ableToStrike) {
    if (ableToStrike) {
      increaseAngle();
    } else {
      gameController.moveControllableX(true);
    }
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

  private void handleStrike(boolean ableToStrike) {
    double angle = (-90 + angleArrow.getRotate()) * (Math.PI / 180);
    double fractionalVelocity = powerMeter.getHeight() / maxPower;
    if (ableToStrike) {
      gameController.hitPointScoringObject(fractionalVelocity, angle);
      //sceneManager.hideStrikingElements();
    }
  }

  private void setAngleArrow(Node node) {
    angleArrow = (Polygon) node;
  }

  private void createNextRoundHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.removeTransitionSheen());
  }

  private void createStartTitleHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.createTitleScene());
  }

  private void setRound(Node node) {
    gameStatusManager.setRoundText(((Text) node));
  }

  private void setTurn(Node node) {
    gameStatusManager.setTurnText(((Text) node));
  }

  private void setScores(Node node) {
    gameStatusManager.setScoreList((ListView<String>) node);
    node.setOnMouseClicked(e -> {sceneManager.getRoot().requestFocus();});
  }

  private void createThemeChangeHandler(Node node) {
    ComboBox<ThemeType> comboBox = (ComboBox<ThemeType>) node;
    comboBox.getItems().addAll(ThemeType.values());
    comboBox.setOnAction(event -> {
      ThemeType selectedTheme = comboBox.getValue();
      sceneManager.changeTheme(selectedTheme);
    });
  }

  private void createNewGameHandler(Node node) {
    node.setOnMouseClicked(e -> gameController.createNewWindow());
  }

  private void createLoginHandler(Node node){
    TextField username = new TextField();
    TextField password = new PasswordField(); //maybe set this as a password in the enum...?
    node.setOnMouseClicked(e -> gameController.openLogin(username.getText(), password.getText()));
    //add another or continue to play (new screen) shows current players like is this good or move on
  }

  private void createUserCreatorHandler(Node node){
    TextField username = new TextField();
    TextField password = new PasswordField(); //maybe set this as a password in the enum...?
    node.setOnMouseClicked(e -> gameController.openCreateUser(username.getText(), password.getText()));

  }

  private void createPasswordHandler(Node node) {
    //userName = (Textfield)node;
  }

  private void createUsernameHandler(Node node) {
  }


}
