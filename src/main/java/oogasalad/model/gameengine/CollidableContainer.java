package oogasalad.model.gameengine;

import java.util.Map;
import oogasalad.model.api.GameRecord;

public class CollidableContainer {

  private final Map<Integer, Collidable> myCollidables;

  public CollidableContainer(Map<Integer, Collidable> collidables) {
    myCollidables = collidables;
  }

  public Collidable getCollidable(int objectId) {
    return myCollidables.get(objectId);
  }

  public boolean checkStatic() {
    for (Collidable c : myCollidables.values()) {
      if (c.getVelocityX() != 0 || c.getVelocityY() != 0) { //should it be getting current or
        // next velo?
        return false;
      }
    }
    return true;
  }

  public void update(double dt) {
    for(Collidable c : myCollidables.values()) {
      c.move(dt);
      c.update();
    }
  }
}
