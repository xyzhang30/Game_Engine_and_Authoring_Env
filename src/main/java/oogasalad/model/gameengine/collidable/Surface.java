package oogasalad.model.gameengine.collidable;

public class Surface extends Collidable {
  private final double mu;
  private static final double g = 10;
  public Surface(int id, double mass, double x, double y, boolean visible, double mu) {
    super(id, mass, x, y, visible);
    this.mu = mu;
  }

  @Override
  public double[] calculateNewSpeed(Collidable other, double dt) {
    double oldVelocityX = other.getVelocityX();
    double oldVelocityY = other.getVelocityY();
    System.out.println(getCollidableRecord());
    if(other.getVelocityX()==0 && other.getVelocityY()==0 && getVelocityX()==0 && getVelocityY()==0) {
      return new double [] {0,0};
    }
    double xv = oldVelocityX - mu * g * dt * (oldVelocityX / Math.hypot(oldVelocityX,
        oldVelocityY));

    double yv = oldVelocityY - mu * g * dt * (oldVelocityY / Math.hypot(oldVelocityX,
        oldVelocityY));
    if(oldVelocityY*yv < 0)  yv = 0;
    if(oldVelocityX*xv < 0)  xv = 0;

    return new double [] {xv, yv};
  }

}
