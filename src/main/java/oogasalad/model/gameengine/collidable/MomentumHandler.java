package oogasalad.model.gameengine.collidable;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.api.CollidableRecord;

public class MomentumHandler implements CollisionHandler {

  private final int id1;
  private final int id2;
  public MomentumHandler(int id1, int id2) {
    this.id1 = id1;
    this.id2 = id2;
  }

  @Override
  public void handleCollision(CollidableContainer collidableContainer, double dt) {
    CollidableRecord c1 = collidableContainer.getCollidableRecord(id1);
    CollidableRecord c2 = collidableContainer.getCollidableRecord(id2);
    List<Double> first = new ArrayList<>(getFirstInfo(c1, c2));
    first.addAll(getFirstInfo(c2,c1));
    collidableContainer.updateSpeeds(id1, id2,
        first.stream().mapToDouble(Double::doubleValue).toArray()); //chat gpt wrote this line
  }

  private static List<Double> getFirstInfo(CollidableRecord c1, CollidableRecord c2) {
    if (c2.mass() == Double.POSITIVE_INFINITY) {
      if (c1.y() + c1.width() / 2 <= c2.y() + c1.width() / 2
          || c1.y() - c1.width() / 2
          >= c2.y() + c2.height() - c1.width() / 2) {
        return List.of(c1.velocityX(), -c1.velocityY());
      } else {
        return List.of(-c1.velocityX(), c1.velocityY());
      }
    }
    else if (c1.mass() == Double.POSITIVE_INFINITY) {
      return List.of(0.0,0.0);
      }
    else {
      double massSum = c1.mass() + c2.mass();
      double xv =
          (2 * c2.mass() * c2.velocityX() + (c1.mass() - c2.mass()) * c1.velocityX()) / massSum;
      double yv =
          (2 * c2.mass() * c2.velocityY() + (c1.mass() - c2.mass()) * c1.velocityY()) / massSum;
      return List.of(xv, yv);
    }
  }

}
