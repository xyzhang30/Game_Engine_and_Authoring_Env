package oogasalad.model.gameengine.collidable;

import oogasalad.model.api.CollidableRecord;

public class FrictionHandler implements CollisionHandler {

  private static final double g = 10;
  private final int id1;
  private final int id2;
  public FrictionHandler(int id1, int id2) {
    this.id1 = id1;
    this.id2 = id2;
  }

  @Override
  public void handleCollision(CollidableContainer collidableContainer, double dt) {
    CollidableRecord c1 = collidableContainer.getCollidableRecord(id1);
    CollidableRecord c2 = collidableContainer.getCollidableRecord(id2);
      double firstNewVelocityX =
          c1.velocityX() - g * c2.mu() * dt - c1.velocityX() / Math.hypot(c1.velocityX()
              , c1.velocityY());
      double firstNewVelocityY =
          c1.velocityY() - g * c2.mu() * dt - c1.velocityY() / Math.hypot(c1.velocityX()
              , c1.velocityY());

      double secondNewVelocityX =
        c2.velocityX() - g * c1.mu() * dt - c2.velocityX() / Math.hypot(c2.velocityX()
            , c2.velocityY());
    double secondNewVelocityY =
        c2.velocityY() - g * c1.mu() * dt - c2.velocityY() / Math.hypot(c2.velocityX()
            , c2.velocityY());

    collidableContainer.updateSpeeds(id1, id2, new double [] {firstNewVelocityX, firstNewVelocityY,
        secondNewVelocityX,
        secondNewVelocityY});
  }

}