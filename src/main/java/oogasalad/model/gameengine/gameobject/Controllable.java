package oogasalad.model.gameengine.gameobject;

public interface Controllable {
  void allowMoveX();

  void allowMoveY();

  double moveX(boolean positive);
  double moveY(boolean positive);

  GameObject asGameObject();

}
