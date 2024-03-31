package oogasalad.model.api;

import java.util.List;
import oogasalad.Pair;

/**
 * @author Noah Loewy
 */
public interface ExternalGameEngine {

  /**
   * Initiates the game with the given ID.
   */
  void start();

  /**
   * Pauses the current game.
   */
  void pause();

  /**
   * Resumes the paused game.
   */
  void resume();

  /**
   * Provides view with updated GameState as immutable record after each frame
   *
   * @return GameRecord object representing the current Collidables, Scores, etc
   */

  GameRecord update(double dt);

  /**
   * Places primary collidable object at location specified by parameters
   *
   * @param x The x coordinate of new location
   * @param y The y coordinate of new location
   */
  void confirmPlacement(double x, double y);



  void handleCollisions(List<Pair> collisions, double dt);

  /**
   * Applies a velocity to the entity with the provided ID.
   *
   * @param magnitude The magnitude of the force to apply.
   * @param direction The direction of the force to apply.
   * @param id        The ID of the entity to apply the force to.
   */
  void applyInitialVelocity(double magnitude, double direction, int id);

  /**
   * Resets the game to its initial state.
   */
  void reset();
}
