package oogasalad.model.gameengine.collidable;

public class NullControllable implements Controllable {

  private Collidable collidable;
  public NullControllable(Collidable c) {
    collidable = c;
  }
  @Override
  public void applyInitialVelocity(double magnitude, double direction) {
//do nothing
  }
  public Collidable getCollidable() {
    return collidable;
  }

  @Override
  public boolean canControl() {
    return false;
  }

  // Implement other Controllable interface methods if needed
}