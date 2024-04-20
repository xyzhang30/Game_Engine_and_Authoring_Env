package oogasalad.view.game_environment.non_game_environment_scene;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.shape.Rectangle;
import oogasalad.view.controller.GameController;
import oogasalad.view.enums.SceneElementEventType;
import oogasalad.view.enums.SceneType;

public class SceneElementHandler {

  private final GameController gameController;
  private final SceneManager sceneManager;
  private double maxPower;
  private double minPower;

  public SceneElementHandler(GameController gameController, SceneManager sceneManager) {
    this.gameController = gameController;
    this.sceneManager = sceneManager;
  }

  public void createElementHandler(Node node, String event) {
    checkForSceneChangeEvent(node, event);
    checkForZoomEvent(node, event);
    checkForGamePlayChangeEvent(node, event);
    checkForStrikingEvent(node, event);
  }

  private void checkForGamePlayChangeEvent(Node node, String event) {
    switch (SceneElementEventType.valueOf(event)) {
      case PAUSE -> {
        createPauseHandler(node);
      }
      case RESUME -> {
        createResumeHandler(node);
      }
    }
  }

  private void checkForZoomEvent(Node node, String event) {
    switch (SceneElementEventType.valueOf(event)) {
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

  private void checkForSceneChangeEvent(Node node, String event) {
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

  private void checkForStrikingEvent(Node node, String event) {
    System.out.println(SceneElementEventType.valueOf(event));
    switch (SceneElementEventType.valueOf(event)) {
      case POWER_HEIGHT -> {
        getMaxPower(node);
      }
      case SET_POWER -> {
        getMinPower(node);
        createPowerHandler(node);
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

  private void createPauseHandler(Node node) {
    node.setOnMouseClicked((e -> gameController.pauseGame()));
  }

  private void createResumeHandler(Node node) {
    node.setOnMouseClicked(e -> gameController.resumeGame());
  }

  private void getMaxPower(Node node) {
    maxPower = ((Rectangle) node).getHeight();
  }

  private void getMinPower(Node node) {
    minPower = ((Rectangle) node).getHeight();
  }

  private void createPowerHandler(Node node) {
    Scene scene = sceneManager.getScene();
    Rectangle powerMeter = (Rectangle) node;
    scene.getRoot().setOnKeyPressed(e -> {
      switch (e.getCode()) {
        case UP: {
          increasePower(powerMeter);
          break;
        }
        case DOWN: {
          decreasePower(powerMeter);
          break;
        }
        case ENTER: {
          handleStrike(powerMeter);
          break;
        }
      }
    });
  }

  private void increasePower(Rectangle powerMeter) {
    if (powerMeter.getHeight() < maxPower - 3 * minPower) {
      powerMeter.setLayoutY(powerMeter.getLayoutY() - 10);
      powerMeter.setHeight(powerMeter.getHeight() + 10);
    }
  }

  private void decreasePower(Rectangle powerMeter) {
    if (powerMeter.getHeight() > minPower) {
      powerMeter.setLayoutY(powerMeter.getLayoutY() + 10);
      powerMeter.setHeight(powerMeter.getHeight() - 10);
    }
  }

  private void handleStrike(Rectangle powerMeter) {
    //ableToHit = false;
    //double angle = Math.toRadians(angleArrow.getAngle() - 90);
    double fractionalVelocity = powerMeter.getHeight() / maxPower;
    //TODO: remove hard coded angle
    gameController.hitPointScoringObject(fractionalVelocity, 0.45);
  }

}
