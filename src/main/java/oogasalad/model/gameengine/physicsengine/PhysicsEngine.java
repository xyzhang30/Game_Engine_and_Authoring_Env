package oogasalad.model.gameengine.physicsengine;

import java.util.ArrayList;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameengine.physicsengine.Vector2D;
import java.util.List;

public class PhysicsEngine {

  // Constants
  public static final Vector2D GRAVITY = new Vector2D(0, 9.81); //proper number for gravity
  public static final double COEFFICIENT_OF_RESTITUTION = 0.7; //elasticity (bouncing not perfectly elastic)

    private List<PhysicsObject> physicsObjects;
    private Vector2D globalGravity = new Vector2D(0, -9.8);

    public PhysicsEngine() {
      this.physicsObjects = new ArrayList<>();
    }

    public void addPhysicsObject(PhysicsObject obj) {
      physicsObjects.add(obj);
    }

    public void update(double deltaTime) {
      for (PhysicsObject obj : physicsObjects) {
        obj.applyForce(globalGravity.multiply(obj.getMass())); // Apply gravity
        obj.update(deltaTime); // Update physics object state
      }
      detectAndResolveCollisions(); // Implement collision detection and resolution
    }

    private void detectAndResolveCollisions() {
      // Collision detection and resolution logic here
    }


}
