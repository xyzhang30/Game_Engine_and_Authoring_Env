package oogasalad.model.gameengine.gameobject.scoreable;

import oogasalad.model.gameengine.gameobject.GameObject;

public class DefaultScoreable implements Scoreable {

  private double tempScore;
  private GameObject gameObject;
  public DefaultScoreable(GameObject go) {
    gameObject = go;
  }
  @Override
  public void setTemporaryScore(double tempScore) {
    this.tempScore = tempScore;
  }

  @Override
  public double getTemporaryScore() {
    return tempScore;
  }

  @Override
  public void incrementTemporaryScore() {
    tempScore++;
  }

  @Override
  public GameObject asGameObject() {
    return gameObject;
  }
}
