package oogasalad.model.gameengine.collidable;

public class Surface extends Collidable {
  private final double mu;
  private static final double g = 9.81;
  public Surface(int id, double mass, double x, double y, boolean visible, double mu) {
    super(id, mass, x, y, visible);
    this.mu = mu;
  }

  @Override
  public double[] calculateNewSpeed(Collidable other, double dt) {
    // resulting speed for OTHER, not itself
    double oldVelocityX = other.getVelocityX();
    double oldVelocityY = other.getVelocityY();
    double xv = oldVelocityX - mu * g * oldVelocityX / Math.hypot(oldVelocityX, oldVelocityY);
    double yv = oldVelocityY - mu * g * oldVelocityY / Math.hypot(oldVelocityX, oldVelocityY);
    return new double [] {xv, yv};
  }

}
