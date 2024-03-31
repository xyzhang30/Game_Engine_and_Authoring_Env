package oogasalad.model.gameengine.collidable;

import oogasalad.model.api.CollidableRecord;
import oogasalad.model.gameengine.physicsengine.PhysicsEngine;

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
  private final PhysicsEngine myPhysicsEngine;

  private boolean myVisible;

  public Collidable(int id, double mass, double x, double y, PhysicsEngine physicsEngine,
      boolean visible) {
    myId = id;
    myMass = mass;
    myX = x;
    myY = y;
    myVelocityX = 0.0;
    myVelocityY = 0.0;
    myNextX = x;
    myNextY = y;
    myNextVelocityX = 0.0;
    myNextVelocityY = 0.0;
    myPhysicsEngine = physicsEngine;
    myVisible = visible;
  }
  public void onCollision(Collidable other, double dt) {
    double[] result = other.calculateNewSpeed(other, dt);
    myNextVelocityX = result[0];
    myNextVelocityY = result[1];
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
    myVelocityX = myNextVelocityX;
    myVelocityY = myNextVelocityY;
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
  protected int getId() {
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

}
