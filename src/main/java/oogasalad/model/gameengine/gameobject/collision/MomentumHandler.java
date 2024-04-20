package oogasalad.model.gameengine.gameobject.collision;

import java.util.List;
import java.util.function.Supplier;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.gameengine.gameobject.PhysicsHandler;

public class MomentumHandler extends PhysicsHandler {

  public MomentumHandler(int id1, int id2) {
    super(id1, id2);
  }

  @Override
  protected Supplier<List<Double>> makeVelocityFunction(GameObjectRecord c1, GameObjectRecord c2, double dt) {
    return () -> {
      // Check if the other object has infinite mass, typically a wall or a paddle
      if (c2.mass() == Double.POSITIVE_INFINITY) {
        // Calculate the angle in radians
        double angle = Math.toRadians(c2.inclineAngle());

        // Compute the reflection based on the incline angle
        return calculateReflection(c1, angle);
      } else {
        // Handle collisions between two movable objects
        return handleMovableCollision(c1, c2);
      }
    };
  }

  private List<Double> calculateReflection(GameObjectRecord c1, double angle) {
    // Compute normal and tangential components of the velocity relative to the angle
    double normalVelocity = Math.cos(angle) * c1.velocityY() - Math.sin(angle) * c1.velocityX();
    double tangentVelocity = Math.sin(angle) * c1.velocityY() + Math.cos(angle) * c1.velocityX();

    // Reflect the normal component
    normalVelocity = -normalVelocity;

    // Convert back to the global coordinate system
    double newVelocityX = -Math.sin(angle) * normalVelocity + Math.cos(angle) * tangentVelocity;
    double newVelocityY = Math.cos(angle) * normalVelocity + Math.sin(angle) * tangentVelocity;

    return List.of(newVelocityX, newVelocityY);
  }

  private List<Double> handleMovableCollision(GameObjectRecord c1, GameObjectRecord c2) {
    double massSum = c1.mass() + c2.mass();
    double xv = (2 * c2.mass() * c2.velocityX() + (c1.mass() - c2.mass()) * c1.velocityX()) / massSum;
    double yv = (2 * c2.mass() * c2.velocityY() + (c1.mass() - c2.mass()) * c1.velocityY()) / massSum;
    return List.of(xv, yv);
  }
}
