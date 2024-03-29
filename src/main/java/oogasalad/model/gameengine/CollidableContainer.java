package oogasalad.model.gameengine;

import java.util.Map;

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
}
