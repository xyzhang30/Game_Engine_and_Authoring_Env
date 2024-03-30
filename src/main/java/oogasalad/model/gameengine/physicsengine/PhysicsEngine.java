package oogasalad.model.gameengine.physicsengine;

import java.util.List;
import oogasalad.model.api.CollidableRecord;

public interface PhysicsEngine {

  CollidableRecord handleCollision(CollidableRecord myCollidable, CollidableRecord otherCollidable);

  void setSurfaces(List<SurfaceRecord> surfaces);

  CollidableRecord move(CollidableRecord collidable, double dt);
}
