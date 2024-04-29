package oogasalad.view.scene_management.scene_element.scene_element_handler;

import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_managers.SceneManager;

public abstract class Handler {

  private final GameController gameController;
  private final SceneManager sceneManager;


  public Handler(GameController gameController, SceneManager sceneManager) {
    this.gameController = gameController;
    this.sceneManager = sceneManager;
    createEventMap();
  }

  protected abstract void createEventMap();

  protected GameController getGameController() {
    return gameController;
  }

  protected SceneManager getSceneManager() {
    return sceneManager;
  }


}
