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

  public void handleCollision(CollidableContainer collidableContainer, double dt) {
    CollidableRecord cr1 = collidableContainer.getCollidableRecord(id1);
    CollidableRecord cr2 = collidableContainer.getCollidableRecord(id2);
    Collidable c1 = collidableContainer.getCollidable(id1);
    Collidable c2 = collidableContainer.getCollidable(id2);
    c1.calculateNewSpeeds(makeVelocityFunction(cr1, cr2, dt));
    c2.calculateNewSpeeds(makeVelocityFunction(cr2, cr1, dt));
    c1.updatePostCollisionVelocity();
    c2.updatePostCollisionVelocity();
  }

  protected abstract Supplier<List<Double>> makeVelocityFunction(CollidableRecord c1,
      CollidableRecord c2,
      double dt);
}
