package oogasalad.model.gameengine.collidable.ownable;

import oogasalad.model.gameengine.collidable.GameObject;

public interface Scoreable {

  public void setTemporaryScore(double tempScore);
  public double getTemporaryScore();
  public void incrementTemporaryScore();
  public GameObject asGameObject();

}
