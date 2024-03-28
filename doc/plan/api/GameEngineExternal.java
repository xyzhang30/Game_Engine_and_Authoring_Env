/**
 * @author Noah Loewy
 */
interface GameEngineExternal extends StaticStateListener {

  /**
   * Initiates the game with the given ID.
   * @param id The ID of the game to start.
   */
  void start(int id);

  /**
   * Pauses the current game.
   */
  void pause();

  /**
   * Resumes the paused game.
   */
  void resume();

  GameRecord update();
  void confirmPlacement(double x, double y);
  /**
   * Updates the game state based on the provided IDs.
   * @param id1 The ID of the first entity to update.
   * @param id2 The ID of the second entity to update.
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
