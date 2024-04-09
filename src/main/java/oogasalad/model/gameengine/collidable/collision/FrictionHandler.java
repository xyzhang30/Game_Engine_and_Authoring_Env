package oogasalad.model.gameengine.collidable.collision;

import java.util.List;
import java.util.function.Supplier;
import oogasalad.model.api.CollidableRecord;
import oogasalad.model.gameengine.collidable.PhysicsHandler;

public class FrictionHandler extends PhysicsHandler {

  private static final double C = 40;
  private static final double g = 10;

  public FrictionHandler(int id1, int id2) {
    super(id1, id2);
  }

  @Override
  protected Supplier<List<Double>> makeVelocityFunction(CollidableRecord c1, CollidableRecord c2,
      double dt) {
    return () -> {
      if (c1.velocityX() == 0 && c1.velocityY() == 0) {
        return List.of(0.0, 0.0);
      }
      double firstNewVelocityX =
          c1.velocityX() - C * g * c2.mu() * dt * (c1.velocityX() / Math.hypot(c1.velocityX(),
              c1.velocityY()));
      double firstNewVelocityY =
          c1.velocityY() - C * g * c2.mu() * dt * (c1.velocityY() / Math.hypot(c1.velocityX(),
              c1.velocityY()));

      if (Math.abs(firstNewVelocityX) < 10) {
        firstNewVelocityX = 0;
      }

      if (Math.abs(firstNewVelocityY) < 10) {
        firstNewVelocityY = 0;
      }

      return List.of(firstNewVelocityX, firstNewVelocityY);
    };
  }
}

