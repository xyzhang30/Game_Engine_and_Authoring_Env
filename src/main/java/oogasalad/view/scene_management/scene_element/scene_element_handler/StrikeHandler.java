package oogasalad.view.scene_management.scene_element.scene_element_handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
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
    }
  }

  private void setAngleArrow(Node node) {
    angleArrow = (Arrow) node;
  }

}
