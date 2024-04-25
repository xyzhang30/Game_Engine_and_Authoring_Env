package oogasalad.model.api;


/**
 * The ExternalGameEngine interface defines methods for the GameController to interact with the game
 * engine. It provides functionality for updating the game state, applying initial velocities to
 * game objects, and resetting the game to its initial state.
 *
 * @author Noah Loewy
 */
public interface ExternalGameEngine {

  /**
   * Represents a timestep update for a game. Within this timestep, all physics helper functions are
   * called to represent movement, collision detection, and event handling in the backend. The front
   * end then received an immutable record.
   *
   * @param dt the amount of time elapsed in this timestep
   * @return GameRecord, an immutable representation of the current state of the game
   */
  GameRecord update(double dt);

  /**
   * Applies a velocity to the entity with the provided ID.
   *
   * @param magnitude The magnitude of the force to apply.
   * @param direction The direction of the force to apply.
   */

  void applyInitialVelocity(double magnitude, double direction);

  /**
   * Resets the game to its initial state.
   */
  void reset();

  /**
   * Updates the X Position of the active controllable by an amount preset in game rules.
   *
   * @param positive true if x position is increasing, false if decreasing
   */
  void moveActiveControllableX(boolean positive);

  /**
   * Updates the Y Position of the active controllable by an amount preset in game rules.
   *
   * @param positive true if y position is increasing, false if decreasing
   */
  void moveActiveControllableY(boolean positive);
}
