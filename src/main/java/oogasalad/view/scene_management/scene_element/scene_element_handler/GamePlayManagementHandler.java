package oogasalad.view.scene_management.scene_element.scene_element_handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import oogasalad.view.api.enums.SceneElementEvent;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_managers.SceneManager;

/**
 * The GamePlayManagementHandler class handles events related to gameplay management, such as
 * pausing, resuming, saving, and loading games. It maps scene element events to their respective
 * event handlers.
 */
public class GamePlayManagementHandler extends Handler {

  private Map<SceneElementEvent, Consumer<Node>> eventMap;

  /**
   * Constructs a GamePlayManagementHandler with the specified GameController and SceneManager.
   * Initializes the event map by creating the mapping between SceneElementEvents and their
   * respective handlers.
   *
   * @param gameController The game controller for managing game state and behavior.
   * @param sceneManager   The scene manager for handling scene transitions and updates.
   */
  public GamePlayManagementHandler(GameController gameController, SceneManager sceneManager) {
    super(gameController, sceneManager);
  }


  protected void createEventMap() {
    eventMap = new HashMap<>();
    eventMap.put(SceneElementEvent.PAUSE, this::createPauseHandler);
    eventMap.put(SceneElementEvent.RESUME, this::createResumeHandler);
    eventMap.put(SceneElementEvent.SAVE, this::createSaveHandler);
    eventMap.put(SceneElementEvent.CHANGE_MOD, this::createChangeModHandler);
  }

  private void createSaveHandler(Node node) {
    node.setOnMouseClicked((e -> {
      getSceneManager().getRoot().requestFocus();
      getGameController().saveGame();
    }));
  }

  private void createPauseHandler(Node node) {
    node.setOnMouseClicked((e -> {
      getSceneManager().createPauseDisplay();
      getGameController().pauseGame();
      getSceneManager().getRoot().requestFocus();
    }));
  }

  private void createResumeHandler(Node node) {
    node.setOnMouseClicked(e -> {
      getSceneManager().removePauseSheen();
      getSceneManager().getRoot().requestFocus();
      getGameController().resumeGame();
    });
  }

  private void createChangeModHandler(Node node) {
    ComboBox<String> comboBox = (ComboBox<String>) node;
    comboBox.getItems().addAll(getGameController().getMods());
    comboBox.setOnAction(event -> {
      String selectedMod = comboBox.getValue();
      getGameController().changeMod(selectedMod);
      getSceneManager().getRoot().requestFocus();
    });
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

}
