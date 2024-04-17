package oogasalad.model.gameengine.gameobject.scoreable;

import oogasalad.model.gameengine.gameobject.GameObject;

public interface Scoreable {

  public void setTemporaryScore(double tempScore);
  public double getTemporaryScore();
  public void incrementTemporaryScore();
  public GameObject asGameObject();

}
