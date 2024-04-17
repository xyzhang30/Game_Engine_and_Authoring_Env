package oogasalad.model.gameengine.collidable;

import java.util.List;
import java.util.function.Supplier;
import oogasalad.model.api.GameObjectRecord;

public abstract class PhysicsHandler {

  private final int id1;
  private final int id2;

  public PhysicsHandler(int id1, int id2) {
    this.id1 = id1;
    this.id2 = id2;
  }

  public void handleCollision(GameObjectContainer gameObjectContainer, double dt) {
    GameObjectRecord gor1 = gameObjectContainer.getCollidableRecord(id1);
    GameObjectRecord gor2 = gameObjectContainer.getCollidableRecord(id2);
    GameObject go1 = gameObjectContainer.getGameObject(id1);
    GameObject go2 = gameObjectContainer.getGameObject(id2);
    go1.calculateNextSpeeds(makeVelocityFunction(gor1, gor2, dt));
    go2.calculateNextSpeeds(makeVelocityFunction(gor2, gor1, dt));
    go1.updatePostCollisionVelocity();
    go2.updatePostCollisionVelocity();
  }

  protected abstract Supplier<List<Double>> makeVelocityFunction(GameObjectRecord c1,
      GameObjectRecord c2,
      double dt);
}
