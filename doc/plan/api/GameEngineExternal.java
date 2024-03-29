/**
 * @author Noah Loewy
 */
interface GameEngineExternal {

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

record GameRecord(List<CollidableRecord> c, List<ScoreboardRecord> s) { }
record LogicRecord(List<Player>, int round, int turn, int subturn, int stage) { }
record CollidableRecord(int collidableId, double x, double y, double width, double height) { }

record Rules(int maxRounds, Map<Integer<Integer, Consumer<GameManager>>> collisionHandler,
             TurnPolicy policy) //TurnPolicy, collisionHandler would need to be able to update
// the gamestate properly and would need to be defined in data API

