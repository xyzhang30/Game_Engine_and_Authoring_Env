package oogasalad.model.gameengine.physicsengine;

import java.util.List;
import oogasalad.model.api.CollidableRecord;

public class StandardEngine implements PhysicsEngine {

  private List<SurfaceRecord> mySurfaces;

  public StandardEngine(List<SurfaceRecord> surfaces) {
    mySurfaces = surfaces;
  }

  @Override
  public CollidableRecord handleCollision(CollidableRecord myCollidable, CollidableRecord otherCollidable) {
    double totalMass = myCollidable.mass() + otherCollidable.mass();
    double newVelocityX = ((myCollidable.mass() - otherCollidable.mass()) * myCollidable.velocityX()
        + 2 * otherCollidable.mass() * otherCollidable.velocityX()) / totalMass;
    double newVelocityY = ((myCollidable.mass() - otherCollidable.mass()) * myCollidable.velocityY()
        + 2 * otherCollidable.mass() * otherCollidable.velocityY()) / totalMass;
    return new CollidableRecord(myCollidable.id(), myCollidable.mass(),
        myCollidable.x(), myCollidable.y(), newVelocityX, newVelocityY);
  }

  @Override
  public void setSurfaces(List<SurfaceRecord> surfaces) {
    mySurfaces = surfaces;
  }

  @Override
  public CollidableRecord move(CollidableRecord collidable, double dt) {
    //TODO
    //get the surface that its on?? or should we already have it
    //update velo based on the coefficient of friction
    //the below is trivial implementation for no friction
    return new CollidableRecord(collidable.id(), collidable.mass(),
        collidable.x()+dt*collidable.velocityX(), collidable.y()+dt*collidable.velocityY(),
        collidable.velocityX(), collidable.velocityY());
  }

}
