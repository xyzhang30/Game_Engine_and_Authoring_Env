package oogasalad.model.gameparser;

import oogasalad.model.api.data.CollidableObject;
import oogasalad.model.gameengine.collidable.GameObject;
import oogasalad.model.gameengine.collidable.DefaultStrikeable;
import oogasalad.model.gameengine.collidable.ownable.DefaultScoreable;

public class CollidableFactory {

  public static GameObject createCollidable(CollidableObject co) {

    GameObject c = new GameObject(
        co.collidableId(),
        co.mass(),
        co.position().xPosition(),
        co.position().yPosition(),
        co.properties().contains("visible") && !co.properties().contains("controllable"),
        co.staticFriction(),
        co.kineticFriction(),
        co.dimension().xDimension(),
        co.dimension().yDimension(),
        co.shape());
    if(co.properties().contains("controllable")) {
      c.addStrikeable(new DefaultStrikeable(c));
      c.addScoreable(new DefaultScoreable(c));
    }
    return c;
  }
}
