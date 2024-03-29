package oogasalad.engine.api;

/**
 * @author Noah Loewy
 */
public interface ExternalGameEngine {

  /**
   * Initiates the game with the given ID.
   * @param id The ID of the game to start.
   */
  public void start(int id);

  /**
   * Pauses the current game.
   */
  public void pause();

  /**
   * Resumes the paused game.
   */
  public void resume();

  /**
   * Provides view with updated GameState as immutable record after each frame
   * @return GameRecord object representing the current Collidables, Scores, etc
   */
  public GameRecord update();

  public void confirmPlacement(double x, double y);
  /**
   * Updates the game state based on the provided IDs.
   * @param id1 The ID of the first entity to update.
   * @param id2 The ID of the second entity to update.
   */
  public void collision(int id1, int id2);

  /**
   * Applies a velocity to the entity with the provided ID.
   * @param magnitude The magnitude of the force to apply.
   * @param direction The direction of the force to apply.
   * @param id The ID of the entity to apply the force to.
   */
  public void applyInitialVelocity(double magnitude, double direction, int id);

  /**
   * Resets the game to its initial state.
   */
  public void reset();
}
