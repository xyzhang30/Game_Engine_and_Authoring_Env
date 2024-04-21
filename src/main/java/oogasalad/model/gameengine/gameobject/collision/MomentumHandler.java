package oogasalad.model.gameengine.gameobject.collision;

import java.util.List;
import java.util.function.Supplier;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.gameengine.gameobject.PhysicsHandler;

/**
 * @author Konur Nordberg
 */

public class MomentumHandler extends PhysicsHandler {

  public MomentumHandler(int id1, int id2) {
    super(id1, id2);
  }

  protected Supplier<List<Double>> makeVelocityFunction(GameObjectRecord c1, GameObjectRecord c2,
      double dt) {
    return () -> {
      if (c2.inelastic() != c1.inelastic() && c1.phaser()==c2.phaser()) {
        return List.of(0.0,0.0);};
        if (c2.mass() == Double.POSITIVE_INFINITY) {
          if (c1.y() + c1.width() / 2 <= c2.y() + c1.width() / 2
              || c1.y() - c1.width() / 2
              >= c2.y() + c2.height() - c1.width() / 2) {
            return List.of(c1.velocityX(), -c1.velocityY());
          } else {
            return List.of(-c1.velocityX(), c1.velocityY());
          }
        } else if (c1.mass() == Double.POSITIVE_INFINITY) {
          return List.of(0.0, 0.0);
        } else {
          double massSum = c1.mass() + c2.mass();
          double xv =
              (2 * c2.mass() * c2.velocityX() + (c1.mass() - c2.mass()) * c1.velocityX()) / massSum;
          double yv =
              (2 * c2.mass() * c2.velocityY() + (c1.mass() - c2.mass()) * c1.velocityY()) / massSum;
          return List.of(xv, yv);
        }
      };
    }
}
