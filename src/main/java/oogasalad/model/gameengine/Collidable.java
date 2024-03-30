package oogasalad.model.gameengine;

import oogasalad.model.api.CollidableRecord;
import oogasalad.model.gameengine.physicsengine.PhysicsEngine;

public class Collidable {

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
  public void onCollision(Collidable other) {
    CollidableRecord otherRecord = other.getCollidableRecord();
    CollidableRecord afterCollide = myPhysicsEngine.handleCollision(getCollidableRecord(), otherRecord);
    updateFromRecord(afterCollide);
  }

  public CollidableRecord getCollidableRecord() {
    return new CollidableRecord(myId, myMass, myX, myY, myVelocityX, myVelocityY, myVisible);
  }

  public void move(double dt) {
    update();
    CollidableRecord afterMove = myPhysicsEngine.move(getCollidableRecord(), dt);
    updateFromRecord(afterMove);
  }

  public void update() {
    myX = myNextX;
    myY = myNextY;
    myVelocityX = myNextVelocityX;
    myVelocityY = myNextVelocityY;
  }

  private void updateFromRecord(CollidableRecord afterCollide) {
    myNextVelocityX = afterCollide.velocityX();
    myNextVelocityY = afterCollide.velocityY();
    myNextX = afterCollide.x();
    myNextY = afterCollide.y();
  }

}
