package oogasalad.view.game_environment.non_game_environment_scene;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import oogasalad.view.controller.GameController;
import oogasalad.view.enums.SceneElementEventType;

public class SceneElementHandler {
  private final GameController gameController;

  public SceneElementHandler(GameController gameController) {
    this.gameController = gameController;
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
    }

  }

  private void createStartMenuHandler(Node node) {
    node.setOnMouseClicked(e -> gameController.openMenuScreen());
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

}
