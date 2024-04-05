package oogasalad.model.gameengine.physicsengine;

import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameengine.physicsengine.Vector2D;
import java.util.List;

public class PhysicsEngine {

  // Constants
  public static final Vector2D GRAVITY = new Vector2D(0, 9.81); //proper number for gravity
  public static final double COEFFICIENT_OF_RESTITUTION = 0.7; //elasticity (bouncing not perfectly elastic)


}
