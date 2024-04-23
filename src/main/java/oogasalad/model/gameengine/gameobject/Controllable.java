package oogasalad.model.gameengine.gameobject;

public interface Controllable {

  double moveX(boolean positive);

  double moveY(boolean positive);

  GameObject asGameObject();

  void setMovement(int xMovement, int yMovement);
}
