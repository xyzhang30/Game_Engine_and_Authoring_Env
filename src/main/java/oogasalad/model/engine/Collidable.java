package oogasalad.model.engine;

public class Collidable {

  private double myMass;
  private double myX;
  private double myY;
  private double myVelocityX;
  private double myVelocityY;
  private int myId;

  public void onCollision(Collidable other) {
    return null;
  }

  //maybe make primary a decoration?
  public void onForceApplied(double magnitude, double direction) {

  }

}
