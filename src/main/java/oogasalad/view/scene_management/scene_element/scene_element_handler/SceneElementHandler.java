package oogasalad.view.scene_management.scene_element.scene_element_handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import javafx.scene.Node;
import oogasalad.view.api.enums.SceneElementEventType;
import oogasalad.view.controller.DatabaseController;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_element.GameStatusManager;
import oogasalad.view.scene_management.scene_managers.SceneManager;

public class SceneElementHandler {

  private final ChangeSceneEventHandler changeSceneEventHandler;
  private final GameStatManagementHandler gameStatManagementHandler;
  private final LanguageEventHandler languageEventHandler;
  private final GamePlayManagementHandler gamePlayManagementHandler;
  private final LoadGameEventHandler loadGameEventHandler;
  private final StrikeHandler strikeHandler;
  private final DatabaseHandler databaseHandler;
  private Map<SceneElementEventType, BiConsumer<Node, String>> eventTypeMap;

  public SceneElementHandler(GameController gameController, DatabaseController databaseController,
      SceneManager sceneManager,
      GameStatusManager gameStatusManager, List<String> currentPlayersManager) {
    this.changeSceneEventHandler = new ChangeSceneEventHandler(gameController, sceneManager);
    this.gameStatManagementHandler = new GameStatManagementHandler(sceneManager, gameStatusManager);
    this.languageEventHandler = new LanguageEventHandler(sceneManager);
    this.gamePlayManagementHandler = new GamePlayManagementHandler(gameController, sceneManager);
    this.strikeHandler = new StrikeHandler(gameController, sceneManager);
    this.databaseHandler = new DatabaseHandler(gameController,sceneManager, databaseController,
        currentPlayersManager);
    this.loadGameEventHandler = new LoadGameEventHandler(gameController, databaseController,
        databaseHandler);
    createEventTypeMap();
  }

  public void createElementHandler(Node node, String eventType, String event) {
    BiConsumer<Node, String> handler = eventTypeMap.get(SceneElementEventType.valueOf(eventType));
    handler.accept(node, event);
  }

  private void createEventTypeMap() {
    eventTypeMap = new HashMap<>();
    eventTypeMap.put(SceneElementEventType.CHANGE_SCENE,
        changeSceneEventHandler::createElementHandler);
    eventTypeMap.put(SceneElementEventType.SET_LANGUAGE,
        languageEventHandler::createElementHandler);
    eventTypeMap.put(SceneElementEventType.MANAGE_GAME_STATS,
        gameStatManagementHandler::createElementHandler);
    eventTypeMap.put(SceneElementEventType.MANAGE_GAME_PLAY,
        gamePlayManagementHandler::createElementHandler);
    eventTypeMap.put(SceneElementEventType.LOAD_GAME,
        loadGameEventHandler::createElementHandler);
    eventTypeMap.put(SceneElementEventType.STRIKE,
        strikeHandler::createElementHandler);
    eventTypeMap.put(SceneElementEventType.DATABASE, databaseHandler::createElementHandler);
  }
}
