package oogasalad.model.gameengine;

public class Collidable {

  private double myMass;

  private double myX;


  private double myY;
  private double myVelocityX;
  private double myVelocityY;
  private int myId;

  public void onCollision(Collidable other) {
    //call physics engine at some point
  }

  public double getMass() {
    return myMass;
  }

  public double getX() { return myX; }

  public double getY() { return myY; }

  public double getVelocityX() {
    return myVelocityX;
  }
  public double getMyVelocityY() {
    return myVelocityY;
  }
  public int getId() {
    return myId;





}
