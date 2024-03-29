package oogasalad.model;

import oogasalad.model.api.ExternalGameEngine;
import oogasalad.model.api.GameRecord;
import oogasalad.model.engine.LogicManager;
import oogasalad.model.engine.PlayerManager;

/**
 * @author Noah Loewy
 */
public class GameEngine implements ExternalGameEngine {

  /**
   * Initiates the game with the given ID.
   *
   * @param id The ID of the game to start.
   */

  @Override
  public void start(int id) {

  }
  /**
   * Pauses the current game.
   */

  @Override
  public void pause() {

  }
  /**
   * Resumes the paused game.
   */

  @Override
  public void resume() {

  }

  /**
   * Provides view with updated GameState as immutable record after each frame
   *
   * @return GameRecord object representing the current Collidables, Scores, etc
   */
  @Override
  public GameRecord update() {
    return null;
  }

  /**
   * Places primary collidable object at location specified by parameters
   *
   * @param x The x coordinate of new location
   * @param y The y coordinate of new location
   */
  @Override
  public void confirmPlacement(double x, double y) {

  }

  /**
   * Handles collision between Collidables with the provided IDs.
   *
   * @param id1 The ID of the first collidable in collision.
   * @param id2 The ID of the second collidable in collision.
   */
  @Override
  public void collision(int id1, int id2) {

  }

  /**
   * Applies a velocity to the entity with the provided ID.
   *
   * @param magnitude The magnitude of the force to apply.
   * @param direction The direction of the force to apply.
   * @param id        The ID of the entity to apply the force to.
   */
  @Override
  public void applyInitialVelocity(double magnitude, double direction, int id) {

  }

  /**
   * Resets the game to its initial state.
   */
  @Override
  public void reset() {

  }

  /**
   * Retrieves the logic manager responsible for game logic.
   *
   * @return The logic manager.
   */

  LogicManager getLogicManager() {
    return null;
  }

  /**
   * Retrieves the player manager responsible for managing players.
   *
   * @return The player manager.
   */
  PlayerManager getPlayerManager() {
    return null;
  }
}
