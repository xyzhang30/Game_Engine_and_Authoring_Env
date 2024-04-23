package oogasalad.model.gameengine.gameobject;

import java.util.List;
import java.util.function.Supplier;
import oogasalad.model.api.GameObjectRecord;

/**
 * The PhysicsHandler abstract class defines the behavior for handling physics-related operations
 * between colliding GameObjects in the game engine, including calculating the next speeds of the
 * GameObjects involved in a collision and updating their velocities post-collision.
 *
 * <p>Subclasses of PhysicsHandler must implement the makeVelocityFunction method, which defines
 * the velocity function used to calculate the next speeds of the GameObjects involved in a
 * collision.
 *
 * <p>The use of the PhysicsHandler abstract class promotes modularity and extensibility in the
 * game engine's physics handling system, allowing for different collision handling strategies to be
 * implemented by subclasses.
 *
 * @author Noah Loewy
 */
public abstract class PhysicsHandler {

  private final GameObject go1;
  private final GameObject go2;

  /**
   * Constructs a PhysicsHandler with the specified IDs of the GameObjects involved in collisions.
   *
   * @param go1 The first GameObject.
   * @param go2 The second GameObject.
   */

  public PhysicsHandler(GameObject go1, GameObject go2) {
    this.go1 = go1;
    this.go2 = go2;
  }

  /**
   * Handles collision between GameObjects by calculating their next speeds through the use of a
   * concrete velocity function, and then synchronously updating their post-collision velocities.
   *
   * @param dt                  The time step for the collision handling process.
   */
  public void handleCollision(double dt) {
    go1.calculateNextSpeeds(makeVelocityFunction(go1.toGameObjectRecord(), go2.toGameObjectRecord(), dt));
    go2.calculateNextSpeeds(makeVelocityFunction(go2.toGameObjectRecord(), go1.toGameObjectRecord(), dt));
    go1.updatePostCollisionVelocity();
    go2.updatePostCollisionVelocity();
  }

  /**
   * Creates a Supplier function that calculates the next speeds of the GameObjects involved in a
   * collision based on their GameObjectRecords and the time step. The function created depends on
   * the specific types of the two colliding GameObjects, as different pairwise types of GameObjects
   * require different methods of calculating velocity. The concrete implementations of the
   * makeVelocityFunction are found in subclasses.
   *
   * @param c1 The GameObjectRecord of the first GameObject.
   * @param c2 The GameObjectRecord of the second GameObject.
   * @param dt The time step for the collision handling process.
   * @return A Supplier function that calculates the next speeds of the GameObjects.
   */
  protected abstract Supplier<List<Double>> makeVelocityFunction(GameObjectRecord c1,
      GameObjectRecord c2,
      double dt);
}
