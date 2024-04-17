package oogasalad.model.gameparser;

import oogasalad.model.api.data.CollidableObject;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.DefaultControllable;
import oogasalad.model.gameengine.collidable.ownable.DefaultOwnable;

public class CollidableFactory {

  public static Collidable createCollidable(CollidableObject co) {

    Collidable c = new Collidable(
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
      c.addControllable(new DefaultControllable(c));
      c.addOwnable(new DefaultOwnable(c));
    }
    return c;
  }
}
