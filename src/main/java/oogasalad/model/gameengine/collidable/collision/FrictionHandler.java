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
      // Assuming g is 9.81 m/s^2
      double g = 9.81;
      // Assuming normal force calculation remains the same; adjust if considering angles
      double normalForce = c1.mass() * g; // No angle considered here for simplicity
      // Choose between static and kinetic friction based on the object's motion
      double mu = c1.velocityX() == 0 && c1.velocityY() == 0 ? c2.staticMu() : c2.kineticMu();

      // Calculate frictional force
      double frictionForce = mu * normalForce;

      // Calculate frictional acceleration (a = F / m)
      double frictionAcceleration = frictionForce / c1.mass();

      // The velocity's magnitude for calculating unit direction vector
      double velocityMagnitude = Math.sqrt(Math.pow(c1.velocityX(), 2) + Math.pow(c1.velocityY(), 2));

      // Prevent division by zero when velocity is zero
      if (velocityMagnitude == 0) {
        return List.of(0.0, 0.0);
      }

      // Unit vector components in the direction of velocity
      double unitVelocityX = c1.velocityX() / velocityMagnitude;
      double unitVelocityY = c1.velocityY() / velocityMagnitude;

      // Frictional deceleration components
      double frictionDecelerationX = frictionAcceleration * unitVelocityX;
      double frictionDecelerationY = frictionAcceleration * unitVelocityY;

      // New velocity components after applying friction (ensure it doesn't increase speed)
      double newVelocityX = c1.velocityX() - frictionDecelerationX * dt;
      double newVelocityY = c1.velocityY() - frictionDecelerationY * dt;

      // Ensure velocity components do not switch signs due to over-application of friction
      if (Math.signum(newVelocityX) != Math.signum(c1.velocityX())) newVelocityX = 0;
      if (Math.signum(newVelocityY) != Math.signum(c1.velocityY())) newVelocityY = 0;

      return List.of(newVelocityX, newVelocityY);
    };
  }
}

