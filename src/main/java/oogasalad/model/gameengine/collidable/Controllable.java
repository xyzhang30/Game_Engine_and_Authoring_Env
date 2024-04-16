package oogasalad.model.gameengine.collidable;

public interface Controllable {
  void applyInitialVelocity(double magnitude, double direction);
  Collidable getCollidable();
}
