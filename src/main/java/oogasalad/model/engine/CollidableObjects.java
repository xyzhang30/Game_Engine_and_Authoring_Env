package oogasalad.model.engine;

import java.util.Map;

public class CollidableObjects {

  private Map<Integer, Collidable> myCollidables;

  public CollidableObjects(Map<Integer, Collidable> collidables) {
    myCollidables = collidables;
  }
  public Collidable getCollidable (int objectId) {
    return myCollidables.get(objectId);
  }
  public boolean checkStatic() {
    return false;
  }
}
