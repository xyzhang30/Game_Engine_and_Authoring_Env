package oogasalad.model.gameengine.collidable;

public class Moveable extends Collidable {

  public Moveable(int id, double mass, double x, double y,
       boolean visible) {
    super(id, mass, x, y, visible);
  }

  @Override
  public double[] calculateNewSpeed(Collidable other, double dt) { //gets speed of thing its
    if(getMass() == Double.MAX_VALUE) {
      return new double [] {-other.getVelocityX(), -other.getVelocityY()};
    }
    if(other.getMass() == Double.MAX_VALUE) {
      return new double [] {0,0};
    }
    double massSum = other.getMass() + getMass();

    if(other.getVelocityX()==0 && other.getVelocityY()==0 && getVelocityX()==0 && getVelocityY()==0) {
      return new double [] {0,0};
    }
    double xv = (2 * getMass() * getVelocityX() + (other.getMass() - getMass()) * other.getVelocityX()) / massSum;
    double yv = (2 * getMass() * getVelocityY() + (other.getMass() - getMass()) * other.getVelocityY()) / massSum;
    return new double [] {xv, yv};
  }

}
