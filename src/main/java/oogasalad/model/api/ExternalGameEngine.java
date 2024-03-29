package oogasalad.model.api;

/**
 * @author Noah Loewy
 */
public interface ExternalGameEngine {

  /**
   * Initiates the game with the given ID.
   * @param id The ID of the game to start.
   */
  void start(int id);

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
   * @return GameRecord object representing the current Collidables, Scores, etc
   */
  GameRecord update();

  /**
   * Places primary collidable object at location specified by parameters
   * @param x The x coordinate of new location
   * @param y The y coordinate of new location
   */
  void confirmPlacement(double x, double y);

  /**
   * Handles collision between Collidables with the provided IDs.
   * @param id1 The ID of the first collidable in collision.
   * @param id2 The ID of the second collidable in collision.
   */

  void collision(int id1, int id2);

  /**
   * Applies a velocity to the entity with the provided ID.
   * @param magnitude The magnitude of the force to apply.
   * @param direction The direction of the force to apply.
   * @param id The ID of the entity to apply the force to.
   */
  void applyInitialVelocity(double magnitude, double direction, int id);

  /**
   * Resets the game to its initial state.
   */
  void reset();
}
