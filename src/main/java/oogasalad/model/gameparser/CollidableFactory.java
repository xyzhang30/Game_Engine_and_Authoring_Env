package oogasalad.model.gameparser;

import oogasalad.model.api.data.CollidableObject;
import oogasalad.model.gameengine.collidable.Collidable;

public class CollidableFactory {

  public static Collidable createCollidable(CollidableObject co) {
    return new Collidable(
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
  }
}
