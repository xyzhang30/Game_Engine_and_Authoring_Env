package oogasalad.model.gameengine;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import oogasalad.Pair;
import oogasalad.model.api.ExternalGameEngine;
import oogasalad.model.api.GameRecord;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameengine.command.Command;

/**
 * @author Noah Loewy
 */
public class GameEngine implements ExternalGameEngine {

  private PlayerContainer playerContainer;
  private LogicManager logicManager;
  private RulesRecord rules;
  private CollidableContainer collidables;
  private Map<Pair, Command> collisionHandlers;

  public GameEngine(int id) {
    GameLoader loader = new GameLoader(id);
    playerContainer = loader.getPlayerManager();
    logicManager = loader.getLogicManager();
    rules = loader.getRules();
    collidables = loader.getCollidables();
    collisionHandlers = loader.getCollisionHandlers();
  }

  /**
   * Starts the current game
   */
  @Override
  public void start() {

  }

  /**
   * Pauses the current game.
   */

  @Override
  public void pause() {

  }

  /**
   * Resumes the paused game
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
  public GameRecord update(double dt) {
    if(collidables.checkStatic()) {
      //do something with the turn policy / advancing
    }
    collidables.update(dt);
    return new GameRecord(collidables.getCollidableRecords(), playerContainer.getPlayerRecords(),
        logicManager.getRound(), logicManager.getTurn(), logicManager.getSubTurn(),
        logicManager.getStage());
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

  @Override
  public void handleCollisions(List<Pair> collisions, double dt) {
    for(Pair collision : collisions) {
      Collidable collidable1 = collidables.getCollidable(collision.getFirst());
      Collidable collidable2 = collidables.getCollidable(collision.getSecond());
      collidable1.onCollision(collidable2, dt);
      collidable2.onCollision(collidable1, dt);
      Command cmd = collisionHandlers.get(collision);
      cmd.execute(this, collision.getFirst(), collision.getSecond());
    }
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

  public LogicManager getLogicManager() {
    return logicManager;
  }

  /**
   * Retrieves the player manager responsible for managing players.
   *
   * @return The player manager.
   */
  public PlayerContainer getPlayerContainer() {
    return playerContainer;
  }

  public CollidableContainer getCollidables() {
    return collidables;
  }

  public RulesRecord getRules() {
    return rules;
  }
}
