package oogasalad.model.gameengine.gameobject;

public interface Controllable {

  void moveY(double dy);
  void moveX(double dx);
  void rotate(double theta);
  GameObject asGameObject();

}
