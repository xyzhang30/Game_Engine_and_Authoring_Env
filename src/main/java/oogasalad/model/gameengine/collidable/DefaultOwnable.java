package oogasalad.model.gameengine.collidable;

import oogasalad.model.gameengine.player.Player;

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
