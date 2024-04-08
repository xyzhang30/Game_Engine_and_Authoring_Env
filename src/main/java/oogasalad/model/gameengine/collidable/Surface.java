package oogasalad.model.gameengine.collidable;

public class Surface extends Collidable {

  private static final double g = 10;
  private static final double C = 40;

  public Surface(int id, double mass, double x, double y, boolean visible, double mu, double width,
      double height, String shape) {
    super(id, mass, x, y, visible, mu, width, height, shape);
  }

  @Override
  public double[] calculateNewSpeed(Collidable other, double dt) {
    double oldVelocityX = other.getVelocityX();
    double oldVelocityY = other.getVelocityY();
    if (oldVelocityY == 0 && oldVelocityX == 0) {
      return new double[]{0, 0};
    }
    double xv = oldVelocityX - C * getMu() * g * dt * (oldVelocityX / Math.hypot(oldVelocityX,
        oldVelocityY));

    double yv = oldVelocityY - C * getMu() * g * dt * (oldVelocityY / Math.hypot(oldVelocityX,
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
