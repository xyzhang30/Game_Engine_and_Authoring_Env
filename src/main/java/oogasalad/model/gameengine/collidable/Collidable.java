package oogasalad.model.gameengine.collidable;

import oogasalad.model.api.CollidableRecord;

public abstract class Collidable {
  private double myMass;
  private double myX;
  private double myY;
  private double myVelocityX;
  private double myVelocityY;
  private int myId;
  private double myNextX;
  private double myNextY;
  private double myNextVelocityX;
  private double myNextVelocityY;
  private boolean myVisible;

  public Collidable(int id, double mass, double x, double y,
      boolean visible) {
    myId = id;
    myMass = mass;
    myX = x;
    myY = y;
    myVelocityX = 0.0;
    myVelocityY = 0.0;
    oldVelocityX = myVelocityX;
    oldVelocityY = myVelocityY;
    myNextX = x;
    myNextY = y;
    myVisible = visible;
  }
  public void onCollision(Collidable other, double dt) {
    double[] result = other.calculateNewSpeed(this, dt);
    myVelocityX = result[0];
    myVelocityY = result[1];
  }

  public abstract double[] calculateNewSpeed(Collidable other, double dt);

  public CollidableRecord getCollidableRecord() {
    return new CollidableRecord(myId, myMass, myX, myY, myVelocityX, myVelocityY, myVisible);
  }

  public void move(double dt) {
    myNextX = myX + dt * myVelocityX;
    myNextY = myY + dt * myVelocityY;
  }

  public void update() {
    myX = myNextX;
    myY = myNextY;
   }

  public void applyInitialVelocity(double magnitude, double direction) {
    myVelocityX = magnitude * Math.cos(direction);
    myNextVelocityX = myVelocityX;
    myVelocityY = magnitude * Math.sin(direction);
    myNextVelocityY = myVelocityY;

  }

  protected double getVelocityX() {
    return myVelocityX;
  }
  protected double getVelocityY() {
    return myVelocityY;
  }
  protected double getMass() {
    return myMass;
  }
  public int getId() {
    return myId;
  }

  protected boolean getVisible() {
    return myVisible;
  }
  protected double getX() {
    return myX;
  }
  protected double getY() {
    return myY;
  }

  public void placeInitial(double x, double y) {
    myX = x;
    myY = y;
  }
}
