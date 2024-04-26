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

public class GameManagementEventHandler {

  private final SceneManager sceneManager;
  private final GameStatusManager gameStatusManager;
  private Map<SceneElementEvent, Consumer<Node>> eventMap;

  /**
   * Constructs a new instance of the SceneElementHandler class.
   * <p>
   * This constructor initializes a SceneElementHandler object with the specified game controller
   * and scene manager. The game controller is responsible for managing the game state, while the
   * scene manager is responsible for managing different scenes within the game environment.
   * Additionally, the constructor sets the angle increment value to a default of 5 degrees.
   *
   * @param sceneManager      An instance of the `SceneManager` class, responsible for managing
   *                          different scenes within the game environment.
   * @param gameStatusManager An instance of the 'GameStatusManager' class, responsible for managing
   *                          the game elements related to displaying the game status
   */
  public GameManagementEventHandler(SceneManager sceneManager,
      GameStatusManager gameStatusManager) {
    this.sceneManager = sceneManager;
    this.gameStatusManager = gameStatusManager;

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
