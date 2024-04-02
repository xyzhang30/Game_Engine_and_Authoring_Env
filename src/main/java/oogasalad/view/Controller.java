package oogasalad.view;

import oogasalad.model.gameengine.GameEngine;

/**
 * Controller class handles communications between model and view.  This class holds manager class
 * instances to delegate handling the information received from the model.
 * @author Jordan Haytaian
 */
public class Controller {

  private final GameEngine gameEngine;
  private final CollisionManager collisionManager;
  private final SceneManager sceneManager;

  public Controller(int id){
    gameEngine = new GameEngine(id);
    collisionManager = new CollisionManager();
    sceneManager = new SceneManager();
  }

  private void handleCollisions(){

  }



}
