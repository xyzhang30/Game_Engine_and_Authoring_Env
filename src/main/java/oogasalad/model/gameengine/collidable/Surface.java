package oogasalad.model.gameengine.collidable;

import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.physicsengine.PhysicsEngine;

public class Surface extends Collidable {
  private final double mu;
  private static final double g = 9.81;
  public Surface(int id, double mass, double x, double y,
      PhysicsEngine physicsEngine, boolean visible, double mu) {
    super(id, mass, x, y, physicsEngine, visible);
    this.mu = mu;
  }

  @Override
  public double[] calculateNewSpeed(Collidable other, double dt) {
    double oldVelocityX = other.getVelocityX();
    double oldVelocityY = other.getVelocityY();
    double xv = oldVelocityX - mu * g * oldVelocityX / Math.hypot(oldVelocityX, oldVelocityY);
    double yv = oldVelocityY - mu * g * oldVelocityY / Math.hypot(oldVelocityX, oldVelocityY);
    return new double [] {xv, yv};
  }

}
