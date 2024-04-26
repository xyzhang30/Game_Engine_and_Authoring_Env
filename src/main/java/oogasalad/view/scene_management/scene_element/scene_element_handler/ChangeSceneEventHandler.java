package oogasalad.view.scene_management.scene_element.scene_element_handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import oogasalad.view.api.enums.SceneElementEvent;
import oogasalad.view.api.enums.ThemeType;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_managers.SceneManager;

/**
 * The ChangeSceneEventHandler class handles events related to changing scenes in the application.
 * It uses a map of event types to their corresponding event handlers. When an event occurs, it
 * invokes the appropriate event handler for the given event type.
 *
 * @author Jordan Haytaian
 */
public class ChangeSceneEventHandler {

  private final GameController gameController;
  private final SceneManager sceneManager;
  private Map<SceneElementEvent, Consumer<Node>> eventMap;


  /**
   * Constructs a ChangeSceneEventHandler with the specified GameController and SceneManager.
   * Initializes the event map by creating the mapping between SceneElementEvents and their
   * respective handlers.
   *
   * @param gameController The game controller for managing game state and behavior.
   * @param sceneManager   The scene manager for handling scene transitions and updates.
   */
  public ChangeSceneEventHandler(GameController gameController, SceneManager sceneManager) {
    this.gameController = gameController;
    this.sceneManager = sceneManager;
    createEventMap();
  }

  /**
   * Creates an event handler for the specified node and event type. The handler will be invoked
   * when the event occurs on the given node.
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
    eventMap.put(SceneElementEvent.START_LANGUAGE, this::createStartLanguageHandler);
    eventMap.put(SceneElementEvent.START_TITLE, this::createStartTitleHandler);
    eventMap.put(SceneElementEvent.START_MENU, this::createStartMenuHandler);
    eventMap.put(SceneElementEvent.START_AUTHORING, this::createStartAuthoringHandler);
    eventMap.put(SceneElementEvent.START_GAME, this::createStartGameHandler);
    eventMap.put(SceneElementEvent.NEXT_ROUND, this::createNextRoundHandler);
    eventMap.put(SceneElementEvent.NEW_GAME_WINDOW, this::createNewGameHandler);
    eventMap.put(SceneElementEvent.HELP, this::createHelpInstructionsHandler);
    eventMap.put(SceneElementEvent.CHANGE_THEME, this::createThemeChangeHandler);
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

  private void createNextRoundHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.removeTransitionSheen());
  }

  private void createStartTitleHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.createTitleScene());
  }

  private void createNewGameHandler(Node node) {
    node.setOnMouseClicked(e -> gameController.createNewWindow());
  }

  private void createHelpInstructionsHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.createHelpInstructions());
  }

  private void createStartLanguageHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.createLanguageSelectionScene());
  }

  private void createThemeChangeHandler(Node node) {
    ComboBox<ThemeType> comboBox = (ComboBox<ThemeType>) node;
    comboBox.getItems().addAll(ThemeType.values());
    comboBox.setOnAction(event -> {
      ThemeType selectedTheme = comboBox.getValue();
      sceneManager.changeTheme(selectedTheme);
    });
  }

}
