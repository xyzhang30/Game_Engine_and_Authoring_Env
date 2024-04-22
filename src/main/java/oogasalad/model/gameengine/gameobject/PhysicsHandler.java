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

  private final int id1;
  private final int id2;

  /**
   * Constructs a PhysicsHandler with the specified IDs of the GameObjects involved in collisions.
   *
   * @param id1 The ID of the first GameObject.
   * @param id2 The ID of the second GameObject.
   */

  public PhysicsHandler(int id1, int id2) {
    this.id1 = id1;
    this.id2 = id2;
  }

  /**
   * Handles collision between GameObjects by calculating their next speeds through the use of a
   * concrete velocity function, and then synchronously updating their post-collision velocities.
   *
   * @param gameObjectContainer The container storing the GameObjects involved in the collision.
   * @param dt                  The time step for the collision handling process.
   */
  public void handleCollision(GameObjectContainer gameObjectContainer, double dt) {
    GameObjectRecord gor1 = gameObjectContainer.getGameObject(id1).toGameObjectRecord();
    GameObjectRecord gor2 = gameObjectContainer.getGameObject(id2).toGameObjectRecord();
    GameObject go1 = gameObjectContainer.getGameObject(id1);
    GameObject go2 = gameObjectContainer.getGameObject(id2);
    go1.calculateNextSpeeds(makeVelocityFunction(gor1, gor2, dt));
    go2.calculateNextSpeeds(makeVelocityFunction(gor2, gor1, dt));
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
