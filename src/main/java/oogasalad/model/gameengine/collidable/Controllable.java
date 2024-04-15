package oogasalad.model.gameengine.collidable;

@FunctionalInterface
public interface Controllable {
  void applyInitialVelocity(double magnitude, double direction);
}
