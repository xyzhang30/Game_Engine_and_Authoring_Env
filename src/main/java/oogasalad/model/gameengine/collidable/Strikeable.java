package oogasalad.model.gameengine.collidable;

public interface Strikeable {
  void applyInitialVelocity(double magnitude, double direction);
  GameObject asGameObject();
}
