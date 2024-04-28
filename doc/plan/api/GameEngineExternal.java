/**
 * @author Noah Loewy
 */
interface GameEngineExternal {

  /**
   * Initiates the game.
   */
  void start(int round);

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
   * Resets the game to its initial state.
   */
  void reset();
}

record CollidableRecord(int id, double x, double y, double width, double height) {}

record PlayerRecord(int playerId, double score) {}

record GameRecord(List<CollidableRecord> collidables, List<PlayerRecord> players, int round,
                       int turn, int subturn, int stage) {}

