package oogasalad.view.game_environment.non_game_environment_scene;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import oogasalad.view.controller.GameController;
import oogasalad.view.enums.SceneElementEventType;
import oogasalad.view.enums.SceneType;

public class SceneElementHandler {

  private final GameController gameController;
  private final SceneManager sceneManager;

  public SceneElementHandler(GameController gameController, SceneManager sceneManager) {
    this.gameController = gameController;
    this.sceneManager = sceneManager;
  }

  public void createElementHandler(Node node, String event) {
    switch (SceneElementEventType.valueOf(event)) {
      case START_MENU -> {
        createStartMenuHandler(node);
      }
      case START_AUTHORING -> {
        createStartAuthoringHandler(node);
      }
      case START_GAME -> {
        createStartGameHandler(node);
      }
      case ZOOM_IN -> {
        createZoomInHandler(node);
      }
      case ZOOM_OUT -> {
        createZoomOutHandler(node);
      }
      case RESET_ZOOM -> {
        createZoomResetHandler(node);
      }
    }
  }

  private void createStartMenuHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.createNonGameScene(SceneType.MENU));
  }

  private void createStartAuthoringHandler(Node node) {
    node.setOnMouseClicked(e -> gameController.openAuthorEnvironment());
  }

  private void createStartGameHandler(Node node) {
    node.setOnMouseClicked(e -> {
      String game = ((ListView<String>) node).getSelectionModel().getSelectedItem();
      if (game != null) {
        gameController.startGamePlay(game);
      }
    });
  }

  private void createZoomInHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.panelZoomIn());
  }

  private void createZoomOutHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.panelZoomOut());
  }

  private void createZoomResetHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.panelZoomReset());
  }

}
