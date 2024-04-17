package oogasalad.model.gameengine.gameobject;

public interface Strikeable {
  void applyInitialVelocity(double magnitude, double direction);
  GameObject asGameObject();
}
