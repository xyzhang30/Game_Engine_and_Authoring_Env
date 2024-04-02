package oogasalad.view;

import java.util.List;
import oogasalad.model.GameEngine;
import oogasalad.model.api.GameRecord;

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
    List<List<Integer>> collisionList = collisionManager.getIntersections();
    for (List<Integer> idPair : collisionList) {
      int id1 = idPair.get(0);
      int id2 = idPair.get(1);
      gameEngine.collision(id1, id2);
    }
  }

  private void updatePositions(){
    GameRecord record = gameEngine.update();

  }



}
