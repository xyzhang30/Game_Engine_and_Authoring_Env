package oogasalad.model.gameengine.collidable;

public class DefaultOwnable implements Ownable {

  private double tempScore;
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
