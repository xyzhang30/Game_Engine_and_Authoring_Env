package oogasalad.model.gameengine.collidable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.CollidableRecord;

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

  public List<CollidableRecord> getCollidableRecords() {
    List<CollidableRecord> ret = new ArrayList<>();
    for(Collidable collidable : myCollidables.values()) {
      ret.add(new CollidableRecord(collidable.getId(), collidable.getMass(), collidable.getX(),
          collidable.getY(), collidable.getVelocityX(), collidable.getVelocityY(),
          collidable.getVisible()));
    }
    return ret;
  }
}
