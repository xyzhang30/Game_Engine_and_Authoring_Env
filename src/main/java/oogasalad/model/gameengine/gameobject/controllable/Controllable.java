package oogasalad.model.gameengine.gameobject.controllable;

import oogasalad.model.gameengine.gameobject.GameObject;

public interface Controllable {

  double moveX(boolean positive);

  double moveY(boolean positive);

  GameObject asGameObject();

  void setMovement(int xMovement, int yMovement);
}
