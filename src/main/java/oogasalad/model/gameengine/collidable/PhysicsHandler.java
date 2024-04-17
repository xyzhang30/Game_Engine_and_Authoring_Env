package oogasalad.model.gameengine.collidable;

import java.util.List;
import java.util.function.Supplier;
import oogasalad.model.api.CollidableRecord;

public abstract class PhysicsHandler {

  private final int id1;
  private final int id2;

  public PhysicsHandler(int id1, int id2) {
    this.id1 = id1;
    this.id2 = id2;
  }

  public void handleCollision(GameObjectContainer gameObjectContainer, double dt) {
    CollidableRecord cr1 = gameObjectContainer.getCollidableRecord(id1);
    CollidableRecord cr2 = gameObjectContainer.getCollidableRecord(id2);
    GameObject c1 = gameObjectContainer.getCollidable(id1);
    GameObject c2 = gameObjectContainer.getCollidable(id2);
    c1.calculateNextSpeeds(makeVelocityFunction(cr1, cr2, dt));
    c2.calculateNextSpeeds(makeVelocityFunction(cr2, cr1, dt));
    c1.updatePostCollisionVelocity();
    c2.updatePostCollisionVelocity();
  }

  protected abstract Supplier<List<Double>> makeVelocityFunction(CollidableRecord c1,
      CollidableRecord c2,
      double dt);
}
