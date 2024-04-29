package oogasalad.view.scene_management.scene_element.scene_element_handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
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
public class ChangeSceneEventHandler extends Handler {

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
    super(gameController, sceneManager);
  }

  /**
   * Creates an event handler for the specified node and event type. The handler will be invoked
   * when the event occurs on the given node.
   *
   * @param node  The node to which the event handler will be attached.
   * @param event The event type as a string that specifies the event to handle.
   */
  public void createElementHandler(Node node, String event) {
    eventMap.get(SceneElementEvent.valueOf(event)).accept(node);
  }

  protected void createEventMap() {
    eventMap = new HashMap<>();
    eventMap.put(SceneElementEvent.START_LANGUAGE, this::createStartLanguageHandler);
    eventMap.put(SceneElementEvent.START_TITLE, this::createStartTitleHandler);
    eventMap.put(SceneElementEvent.START_MENU, this::createStartMenuHandler);
    eventMap.put(SceneElementEvent.START_AUTHORING, this::createStartAuthoringHandler);
    eventMap.put(SceneElementEvent.PLAYER_FRIENDS, this::createAddFriendsHandler);
    eventMap.put(SceneElementEvent.NEXT_ROUND, this::createNextRoundHandler);
    eventMap.put(SceneElementEvent.NEW_GAME_WINDOW, this::createNewGameHandler);
    eventMap.put(SceneElementEvent.UPDATE_CURRENT_PLAYERS, this::updateCurrentPlayersHandler);
    eventMap.put(SceneElementEvent.HELP, this::createHelpInstructionsHandler);
    eventMap.put(SceneElementEvent.CHANGE_THEME, this::createThemeChangeHandler);
    eventMap.put(SceneElementEvent.GAME_OVER, this::createGameOverSceneHandler);
  }

  private void createStartMenuHandler(Node node) {
    node.setOnMouseClicked(e -> getSceneManager().createMenuScene());
  }

  private void createStartAuthoringHandler(Node node) {
    node.setOnMouseClicked(e -> getGameController().openAuthorEnvironment());
  }

  private void createAddFriendsHandler(Node node) {
    node.setOnMouseClicked(e -> getGameController().openAddFriends());
  }

  private void createNextRoundHandler(Node node) {
    node.setOnMouseClicked(e -> getSceneManager().removeTransitionSheen());
  }

  private void createStartTitleHandler(Node node) {
    node.setOnMouseClicked(e -> getSceneManager().createTitleScene());
  }

  private void createNewGameHandler(Node node) {
    node.setOnMouseClicked(e -> getGameController().createNewWindow());
  }

  private void createHelpInstructionsHandler(Node node) {
    node.setOnMouseClicked(e -> getSceneManager().createHelpInstructions());
  }

  private void createStartLanguageHandler(Node node) {
    node.setOnMouseClicked(e -> getSceneManager().createLanguageSelectionScene());
  }

  private void createGameOverSceneHandler(Node node) {
    node.setOnMouseClicked(e -> getSceneManager().createGameOverScene());
  }

  private void updateCurrentPlayersHandler(Node node) {
    node.setOnMouseClicked(e -> getSceneManager().createCurrentPlayersScene());
  }

  private void createThemeChangeHandler(Node node) {
    ComboBox<ThemeType> comboBox = (ComboBox<ThemeType>) node;
    comboBox.getItems().addAll(ThemeType.values());
    comboBox.setOnAction(event -> {
      ThemeType selectedTheme = comboBox.getValue();
      getSceneManager().changeTheme(selectedTheme);
    });
  }

}
