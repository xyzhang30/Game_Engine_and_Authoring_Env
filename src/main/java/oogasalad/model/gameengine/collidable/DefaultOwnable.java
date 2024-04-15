package oogasalad.model.gameengine.collidable;

public class DefaultOwnable implements Ownable {

  private double score = 0;
  @Override
  public void setTemporaryScore(double tempScore) {
    score = tempScore;
  }

  @Override
  public double getTemporaryScore() {
    return score;
  }

  public void incrementTemporaryScore() {
    score++;
  }
}
