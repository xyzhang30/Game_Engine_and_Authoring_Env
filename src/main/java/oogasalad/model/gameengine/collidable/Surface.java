package oogasalad.model.gameengine.collidable;

public class Surface extends Collidable {

  private static final double g = 10;
  private static final double C = 40;
  private final double mu;

  public Surface(int id, double mass, double x, double y, boolean visible, double mu, double width,
      double height, String shape) {
    super(id, mass, x, y, visible, width, height, shape);
    this.mu = mu;
  }

  @Override
  public double[] calculateNewSpeed(Collidable other, double dt) {
    double oldVelocityX = other.getVelocityX();
    double oldVelocityY = other.getVelocityY();
    if (other.getVelocityX() == 0 && other.getVelocityY() == 0 && getVelocityX() == 0
        && getVelocityY() == 0) {
      return new double[]{0, 0};
    }
    double xv = oldVelocityX - C * mu * g * dt * (oldVelocityX / Math.hypot(oldVelocityX,
        oldVelocityY));

    double yv = oldVelocityY - C * mu * g * dt * (oldVelocityY / Math.hypot(oldVelocityX,
        oldVelocityY));
    if (oldVelocityY * yv < 0) {
      yv = 0;
    }
    if (oldVelocityX * xv < 0) {
      xv = 0;
    }

    return new double[]{xv, yv};
  }

}
