package oogasalad.view.scene_management.scene_element.scene_element_handler;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import oogasalad.view.api.enums.KeyInputType;
import oogasalad.view.api.enums.SceneElementEvent;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_managers.SceneManager;
import oogasalad.view.visual_elements.Arrow;

/**
 * The StrikeHandler class handles events related to striking in a game. It provides event handling
 * for adjusting power and angle when preparing to strike.
 */
public class StrikeHandler {

  private final GameController gameController;
  private final SceneManager sceneManager;
  private final int angleIncrement = 5;
  private Map<SceneElementEvent, Consumer<Node>> eventMap;
  private double maxPower;
  private double minPower;
  private Rectangle powerMeter;
  private Arrow angleArrow;

  /**
   * Constructs a StrikeHandler with the specified GameController and SceneManager. Initializes the
   * event map by creating the mapping between SceneElementEvents and their respective handlers.
   *
   * @param gameController The game controller for managing game state and behavior.
   * @param sceneManager   The scene manager for handling scene transitions and updates.
   */
  public StrikeHandler(GameController gameController, SceneManager sceneManager) {
    this.gameController = gameController;
    this.sceneManager = sceneManager;

    createEventMap();
  }

  /**
   * Creates an event handler for the specified node and event type. The handler will invoke the
   * appropriate event function when the event occurs on the given node.
   *
   * @param node  The node to which the event handler will be attached.
   * @param event The event type as a string that specifies the event to handle.
   */
  public void createElementHandler(Node node, String event) {
    Consumer<Node> consumer = eventMap.get(SceneElementEvent.valueOf(event));
    consumer.accept(node);
  }

  private void createEventMap() {
    eventMap = new HashMap<>();
    eventMap.put(SceneElementEvent.POWER_HEIGHT, this::getMaxPower);
    eventMap.put(SceneElementEvent.SET_POWER, this::createPowerHandler);
    eventMap.put(SceneElementEvent.SET_ANGLE, this::setAngleArrow);
  }

  private void getMaxPower(Node node) {
    maxPower = ((Rectangle) node).getHeight();
  }

  private void createPowerHandler(Node node) {
    powerMeter = (Rectangle) node;
    minPower = powerMeter.getHeight();
    Pane root = sceneManager.getRoot();
    Map<KeyCode, Runnable> strikableKeyMap = createStrikableKeyMap();
    Map<KeyCode, Consumer<Boolean>> controllableKeyMap = createControllableKeyMap();
    root.setOnKeyPressed(e -> {
      if (gameController.getAbleToStrike()) {
        Runnable keyHandler = strikableKeyMap.get(e.getCode());
        if (keyHandler != null) {
          keyHandler.run();
        }
      } else {
        Consumer<Boolean> keyHandler = controllableKeyMap.get(e.getCode());
        if (keyHandler != null) {
          keyHandler.accept(gameController.getAbleToStrike());
        }
      }

    });
  }

  private Map<KeyCode, Runnable> createStrikableKeyMap() {
    Map<KeyCode, Runnable> keyMap = new HashMap<>();
    keyMap.put(gameController.getKey(KeyInputType.ANGLE_LEFT), this::decreaseAngle);
    keyMap.put(gameController.getKey(KeyInputType.ANGLE_RIGHT), this::increaseAngle);
    keyMap.put(gameController.getKey(KeyInputType.POWER_UP), this::increasePower);
    keyMap.put(gameController.getKey(KeyInputType.POWER_DOWN), this::decreasePower);
    //keyMap.put(gameController.getKey(KeyInputType.)), this::handleStrike);
    return keyMap;
  }

  private Map<KeyCode, Consumer<Boolean>> createControllableKeyMap() {
    Map<KeyCode, Consumer<Boolean>> keyMap = new HashMap<>();
    keyMap.put(gameController.getKey(KeyInputType.CONTROLLABLE_UP),
        c -> gameController.moveControllableY(true));
    keyMap.put(gameController.getKey(KeyInputType.CONTROLLABLE_DOWN),
        c -> gameController.moveControllableY(false));
    keyMap.put(gameController.getKey(KeyInputType.CONTROLLABLE_LEFT),
        c -> gameController.moveControllableX(false));
    keyMap.put(gameController.getKey(KeyInputType.CONTROLLABLE_RIGHT),
        c -> gameController.moveControllableX(true));
    return keyMap;
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
    }
  }

  private void setAngleArrow(Node node) {
    angleArrow = (Arrow) node;
  }

}
