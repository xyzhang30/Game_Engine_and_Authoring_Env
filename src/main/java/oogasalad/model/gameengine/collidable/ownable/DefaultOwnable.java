package oogasalad.model.gameengine.collidable.ownable;

import oogasalad.model.gameengine.collidable.GameObject;

public class DefaultOwnable implements Scoreable {

  private double tempScore;
  private GameObject collidable;
  public DefaultOwnable(GameObject c) {
    collidable = c;
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

  public GameObject asCollidable() {
    return collidable;
  }
}
