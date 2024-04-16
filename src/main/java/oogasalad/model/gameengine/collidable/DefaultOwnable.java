package oogasalad.model.gameengine.collidable;

import oogasalad.model.gameengine.player.Player;

public class DefaultOwnable implements Ownable {

  private double tempScore;
  private Player player;
  private Collidable collidable;
  public DefaultOwnable(Collidable c, Player p) {
    collidable = c;
    player = p;
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
}
