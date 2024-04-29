package oogasalad.view.scene_management.scene_element.scene_element_handler;

import java.util.HashMap;
import java.util.Map;
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
public class StrikeHandler extends Handler {

  private final int angleIncrement = 5;
  private Map<SceneElementEvent, Consumer<Node>> eventMap;
  private double maxPower;
  private double minPower;
  private Rectangle powerMeter;
  private Arrow angleArrow;

  /**
   * Constructs a StrikeHandler with the specified getGameController() and SceneManager. Initializes
   * the event map by creating the mapping between SceneElementEvents and their respective
   * handlers.
   *
   * @param gameController The game controller for managing game state and behavior.
   * @param sceneManager   The scene manager for handling scene transitions and updates.
   */
  public StrikeHandler(GameController gameController, SceneManager sceneManager) {
    super(gameController, sceneManager);
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

  protected void createEventMap() {
    eventMap = new HashMap<>();
    eventMap.put(SceneElementEvent.POWER_HEIGHT, this::getMaxPower);
    eventMap.put(SceneElementEvent.SET_POWER, this::createPowerHandler);
    eventMap.put(SceneElementEvent.SET_ANGLE, this::setAngleArrow);
  }

  private void getMaxPower(Node node) {
    maxPower = ((Rectangle) node).getHeight();
  }

  public void createPowerHandler(Node node) {
    powerMeter = (Rectangle) node;
    minPower = powerMeter.getHeight();
    Pane root = getSceneManager().getRoot();
    Map<KeyCode, Runnable> strikableKeyMap = createStrikableKeyMap();
    Map<KeyCode, Consumer<Boolean>> controllableKeyMap = createControllableKeyMap();
    root.setOnKeyPressed(e -> {
      if (getGameController().getAbleToStrike()) {
        Runnable keyHandler = strikableKeyMap.get(e.getCode());
        if (keyHandler != null) {
          keyHandler.run();
        }
      } else {
        Consumer<Boolean> keyHandler = controllableKeyMap.get(e.getCode());
        if (keyHandler != null) {
          keyHandler.accept(getGameController().getAbleToStrike());
        }
      }

    });
  }

  private Map<KeyCode, Runnable> createStrikableKeyMap() {
    Map<KeyCode, Runnable> keyMap = new HashMap<>();
    keyMap.put(getGameController().getKey(KeyInputType.ANGLE_LEFT), this::decreaseAngle);
    keyMap.put(getGameController().getKey(KeyInputType.ANGLE_RIGHT), this::increaseAngle);
    keyMap.put(getGameController().getKey(KeyInputType.POWER_UP), this::increasePower);
    keyMap.put(getGameController().getKey(KeyInputType.POWER_DOWN), this::decreasePower);
    keyMap.put(getGameController().getKey(KeyInputType.STRIKING), this::handleStrike);
    return keyMap;
  }

  private Map<KeyCode, Consumer<Boolean>> createControllableKeyMap() {
    Map<KeyCode, Consumer<Boolean>> keyMap = new HashMap<>();
    keyMap.put(getGameController().getKey(KeyInputType.CONTROLLABLE_LEFT),
        c -> getGameController().moveControllableX(false, getSceneManager().getGameBoardLeftBound(),
            getSceneManager().getGameBoardRightBound()));
    keyMap.put(getGameController().getKey(KeyInputType.CONTROLLABLE_RIGHT),
        c -> getGameController().moveControllableX(true, getSceneManager().getGameBoardLeftBound(),
            getSceneManager().getGameBoardRightBound()));
    return keyMap;
  }

  private void decreaseAngle() {
    if (angleArrow.getRotate() > -180) {
      angleArrow.setRotate(angleArrow.getRotate() - angleIncrement);
    }
  }

  public void increaseAngle() {
    if (angleArrow.getRotate() < 180) {
      angleArrow.setRotate(angleArrow.getRotate() + angleIncrement);
    }
  }

  public void increasePower() {
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
    getGameController().hitPointScoringObject(fractionalVelocity, angle);
  }

  public void setAngleArrow(Node node) {
    angleArrow = (Arrow) node;
  }

}
