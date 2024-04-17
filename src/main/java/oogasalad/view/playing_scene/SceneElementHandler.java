package oogasalad.view.playing_scene;

import javafx.scene.Node;
import oogasalad.view.Controlling.GameController;
import oogasalad.view.enums.SceneElementEvent;

public class SceneElementHandler {

  private final GameController gameController;

  public SceneElementHandler(GameController gameController) {
    this.gameController = gameController;
  }

  public void createElementHandler(Node node, String event) {
    switch (SceneElementEvent.valueOf(event)) {
      case START_MENU -> {
        createStartMenuHandler(node);
      }
      case START_AUTHORING -> {
        createStartAuthoringHandler(node);
      }
    }

  }

  private void createStartMenuHandler(Node node) {
    node.setOnMouseClicked(e -> gameController.openMenuScreen());
  }

  private void createStartAuthoringHandler(Node node) {
    node.setOnMouseClicked(e -> gameController.openAuthorEnvironment());
  }

}
