package oogasalad.model.gameengine.collidable.collision;

import java.util.List;
import java.util.function.Supplier;
import oogasalad.model.api.CollidableRecord;
import oogasalad.model.gameengine.collidable.PhysicsHandler;

public class FrictionHandler extends PhysicsHandler {

  private static final double C = 40;
  private static final double g = 9.81;

  public FrictionHandler(int id1, int id2) {
    super(id1, id2);
  }

  @Override
  protected Supplier<List<Double>> makeVelocityFunction(CollidableRecord c1, CollidableRecord c2,
      double dt) {
    return () -> {
      //need to standardize angle from 0-90 for each case... wrote a method for this
      double inclineAngleRadians = Math.toRadians(normalizeInclineAngle(c2.inclineAngle()));
      double gravityComponent = Math.sin(inclineAngleRadians) * g;  // g is the acceleration due to gravity

      double inclineAngle = Math.toRadians(c2.inclineAngle()); // Convert angle to radians for calculations

      double gravityX = 0, gravityY = 0;
      if (inclineAngle >= 0 && inclineAngle < 90) {  // Up to down
        gravityY = gravityComponent;
      } else if (inclineAngle >= 90 && inclineAngle < 180) {  // Down to up
        gravityY = -gravityComponent;
      } else if (inclineAngle >= 180 && inclineAngle < 270) {  // Left to right
        gravityX = gravityComponent;
      } else if (inclineAngle >= 270 && inclineAngle < 360) {  // Right to left
        gravityX = -gravityComponent;
      }

      // Components of gravity parallel and perpendicular to the incline
//      double gParallelX = g * Math.sin(inclineAngle);
//      double gParallelY = 0; // No vertical movement if incline is only horizontal

      // Initial velocity adjustments due to gravity along the incline
      double initialVelocityX = c1.velocityX() + gravityX * dt;
      double initialVelocityY = c1.velocityY() + gravityY * dt;

      // Calculate the normal force considering the incline (assuming surface is aligned along x-axis)
      double normalForce = c1.mass() * g * Math.cos(inclineAngle);

      // Determine the appropriate friction coefficient
      double mu = (initialVelocityX == 0 && initialVelocityY == 0) ? c2.staticMu() : c2.kineticMu();

      // Calculate the magnitude of frictional force
      double frictionForce = mu * normalForce;

      // Calculate frictional deceleration
      double velocityMagnitude = Math.sqrt(initialVelocityX * initialVelocityX + initialVelocityY * initialVelocityY);
      double frictionDeceleration = frictionForce / c1.mass();

      // Calculate the components of frictional deceleration
      double unitVelocityX = (velocityMagnitude != 0) ? initialVelocityX / velocityMagnitude : 0;
      double unitVelocityY = (velocityMagnitude != 0) ? initialVelocityY / velocityMagnitude : 0;

      double frictionDecelerationX = frictionDeceleration * unitVelocityX * dt;
      double frictionDecelerationY = frictionDeceleration * unitVelocityY * dt;

      // Update velocities considering friction
      double newVelocityX = initialVelocityX - frictionDecelerationX;
      double newVelocityY = initialVelocityY - frictionDecelerationY;

      // Prevent friction from reversing the direction of motion
      if (Math.signum(newVelocityX) != Math.signum(initialVelocityX)) newVelocityX = 0;
      if (Math.signum(newVelocityY) != Math.signum(initialVelocityY)) newVelocityY = 0;
      System.out.println("dt: " + dt + List.of(newVelocityX, newVelocityY));

      return List.of(newVelocityX, newVelocityY);
    };
  }


  private double normalizeInclineAngle(double angle) {
    // Normalize the angle to 0-360
    angle = angle % 360;

    // Convert the angle to a 0-90 degree range
    if (angle > 90 && angle <= 180) {
      angle = 180 - angle; // Down to up
    } else if (angle > 180 && angle <= 270) {
      angle = angle - 180; // Left to right
    } else if (angle > 270 && angle <= 360) {
      angle = 360 - angle; // Right to left
    }
    return angle;
  }
}

