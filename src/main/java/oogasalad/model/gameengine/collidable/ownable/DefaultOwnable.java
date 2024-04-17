package oogasalad.model.gameengine.collidable.ownable;

import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.ownable.Ownable;

public class DefaultOwnable implements Ownable {

  private double tempScore;
  private Collidable collidable;
  public DefaultOwnable(Collidable c) {
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

  public Collidable asCollidable() {
    return collidable;
  }
}
