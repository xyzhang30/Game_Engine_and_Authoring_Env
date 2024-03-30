package oogasalad.model.gameengine;

import java.util.Map;
import oogasalad.model.api.GameRecord;

public class CollidableContainer {

  private Map<Integer, Collidable> myCollidables;

  public CollidableContainer(Map<Integer, Collidable> collidables) {
    myCollidables = collidables;
  }
  public Collidable getCollidable (int objectId) {
    return myCollidables.get(objectId);
  }
  public boolean checkStatic() {
    return false;
  }
  public GameRecord update(double dt) {
    for(Collidable c : myCollidables.values()) {
      c.update();
      c.move(dt);
      c.update();
    }
    return null;
  }
}
