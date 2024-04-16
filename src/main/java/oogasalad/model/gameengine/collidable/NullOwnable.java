package oogasalad.model.gameengine.collidable;

public class NullOwnable implements Ownable {

  @Override
  public void setTemporaryScore(double tempScore) {
    //do nothing
  }

  @Override
  public double getTemporaryScore() {
    return 0;
  }

  @Override
  public void incrementTemporaryScore() {
    //do nothing
  }
}
