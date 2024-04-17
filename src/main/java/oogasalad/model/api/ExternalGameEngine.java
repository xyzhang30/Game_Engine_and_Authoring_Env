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
   * @param id        The ID of the GameObject to apply the force to.
   */
  void applyInitialVelocity(double magnitude, double direction, int id);

  /**
   * Resets the game to its initial state.
   */
  void reset();
}
