package oogasalad.view.scene_management.scene_element.scene_element_handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import oogasalad.view.api.enums.SceneElementEvent;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_element.GameStatusManager;
import oogasalad.view.scene_management.scene_managers.SceneManager;

public class ChangeSceneEventHandler {
  private final GameController gameController;
  private final SceneManager sceneManager;
  private Map<SceneElementEvent, Consumer<Node>> eventMap;

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
  public ChangeSceneEventHandler(GameController gameController, SceneManager sceneManager,
      GameStatusManager gameStatusManager) {
    this.gameController = gameController;
    this.sceneManager = sceneManager;
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
    eventMap.put(SceneElementEvent.START_LANGUAGE, this::createStartLanguageHandler);
    eventMap.put(SceneElementEvent.START_TITLE, this::createStartTitleHandler);
    eventMap.put(SceneElementEvent.START_MENU, this::createStartMenuHandler);
    eventMap.put(SceneElementEvent.START_AUTHORING, this::createStartAuthoringHandler);
    eventMap.put(SceneElementEvent.START_GAME, this::createStartGameHandler);
    eventMap.put(SceneElementEvent.NEXT_ROUND, this::createNextRoundHandler);
    eventMap.put(SceneElementEvent.NEW_GAME_WINDOW, this::createNewGameHandler);
    eventMap.put(SceneElementEvent.HELP, this::createHelpInstructionsHandler);
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
}
