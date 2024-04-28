package oogasalad.view.scene_management.scene_element.scene_element_handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import oogasalad.view.api.enums.SceneElementEvent;
import oogasalad.view.scene_management.scene_element.GameStatusManager;
import oogasalad.view.scene_management.scene_managers.SceneManager;


/**
 * The GameStatManagementHandler class handles events related to managing game-related scenes and status updates.
 * It maps scene element events to their respective event handlers for setting the round, turn, and scores.
 *
 * @author Jordan Haytaian
 */
public class GameStatManagementHandler {

  private final SceneManager sceneManager;
  private final GameStatusManager gameStatusManager;
  private Map<SceneElementEvent, Consumer<Node>> eventMap;

  /**
   * Constructs a GameStatManagementHandler with the specified SceneManager and GameStatusManager.
   * Initializes the event map by creating the mapping between SceneElementEvents and their respective handlers.
   *
   * @param sceneManager The scene manager for handling scene transitions and updates.
   * @param gameStatusManager The game status manager for managing game status displays.
   */
  public GameStatManagementHandler(SceneManager sceneManager,
      GameStatusManager gameStatusManager) {
    this.sceneManager = sceneManager;
    this.gameStatusManager = gameStatusManager;

    createEventMap();
  }

  /**
   * Creates an event handler for the specified node and event type.
   * The handler will invoke the appropriate event function when the event occurs on the given node.
   *
   * @param node The node to which the event handler will be attached.
   * @param event The event type as a string that specifies the event to handle.
   */
  public void createElementHandler(Node node, String event) {
    Consumer<Node> consumer = eventMap.get(SceneElementEvent.valueOf(event));
    consumer.accept(node);
  }

  private void createEventMap() {
    eventMap = new HashMap<>();
    eventMap.put(SceneElementEvent.SET_ROUND, this::setRound);
    eventMap.put(SceneElementEvent.SET_TURN, this::setTurn);
    eventMap.put(SceneElementEvent.SET_SCORE, this::setScores);
  }

  private void setRound(Node node) {
    gameStatusManager.setRoundText(((Text) node));
  }

  private void setTurn(Node node) {
    gameStatusManager.setTurnText(((Text) node));
  }

  private void setScores(Node node) {
    gameStatusManager.setScoreList((ListView<String>) node);
    node.setOnMouseClicked(e -> {
      sceneManager.getRoot().requestFocus();
    });
  }
}
