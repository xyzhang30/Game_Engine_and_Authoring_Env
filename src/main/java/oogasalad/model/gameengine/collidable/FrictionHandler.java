package oogasalad.model.gameengine.collidable;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.api.CollidableRecord;

public class FrictionHandler implements PhysicsHandler {

  private static final double g = 10;
  private static final double C = 40;
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
    List<Double> first = new ArrayList<>(getFirstInfo(c1, c2, dt));
    first.addAll(getFirstInfo(c2, c1, dt));
    collidableContainer.updateSpeeds(id1, id2,
        first.stream().mapToDouble(Double::doubleValue).toArray()); //chat gpt wrote this line
  }


  List<Double> getFirstInfo(CollidableRecord c1, CollidableRecord c2, double dt) {
    if(c1.velocityX()==0 && c1.velocityY()==0) {
      return List.of(0.0,0.0);
    }
    double firstNewVelocityX =
        c1.velocityX() - C * g * c2.mu() * dt * (c1.velocityX() / Math.hypot(c1.velocityX()
            , c1.velocityY()));
    double firstNewVelocityY =
        c1.velocityY() - C * g * c2.mu() * dt * (c1.velocityY() / Math.hypot(c1.velocityX()
            , c1.velocityY()));
    if(Math.abs(firstNewVelocityX) < 10) {
      firstNewVelocityX = 0;
    }
    if(Math.abs(firstNewVelocityY) < 10) {
      firstNewVelocityY = 0;
    }
    return List.of(firstNewVelocityX, firstNewVelocityY);

  }


}