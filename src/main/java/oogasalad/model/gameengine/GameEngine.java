package oogasalad.model.gameengine;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import oogasalad.Pair;
import oogasalad.model.api.ExternalGameEngine;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameparser.GameLoaderModel;


/**
 * @author Noah Loewy
 */
public class GameEngine implements ExternalGameEngine {

  private PlayerContainer playerContainer;
  private RulesRecord rules;
  private CollidableContainer collidables;
  private Map<Pair, List<Command>> collisionHandlers;
  private TurnPolicy turnPolicy;
  private int round;
  private int turn;
  private boolean gameOver;
  private boolean staticState;

  private GameLoaderModel loader;

  public GameEngine(String gameTitle) {
    round = 1;
    turn = 1; //first player ideally should have id 1
    gameOver = false;
    staticState = true;
    loader = new GameLoaderModel(gameTitle);
  }

  /**
   * Starts the current game
   */
  @Override
  public void start(GameLoaderModel loader) {

    playerContainer = loader.getPlayerContainer();
    rules = loader.getRulesRecord();
    collidables = loader.getCollidableContainer();
    collisionHandlers = rules.collisionHandlers();
    turnPolicy = loader.getTurnPolicy();

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
  private GameRecord update(double dt) {
    if(collidables.checkStatic()) {
      staticState = true;
    }
    else {
      staticState = false;
    }
    collidables.update(dt);
    return new GameRecord(collidables.getCollidableRecords(), playerContainer.getPlayerRecords(),
        round, turn, gameOver, staticState);
  }

  @Override
  public GameRecord handleCollisions(List<Pair> collisions, double dt) {
    for(Pair collision : collisions) {
      Collidable collidable1 = collidables.getCollidable(collision.getFirst());
      Collidable collidable2 = collidables.getCollidable(collision.getSecond());
      collidable1.onCollision(collidable2, dt);
      collidable2.onCollision(collidable1, dt);
      collidable1.updatePostCollisionVelocity();
      collidable2.updatePostCollisionVelocity();
      if(collisionHandlers.containsKey(collision)){
        for(Command cmd : collisionHandlers.get(collision)) {
          cmd.execute(this);
        }
      }

    }
    return update(dt);
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
    Collidable collidable = collidables.getCollidable(id);
    collidable.applyInitialVelocity(magnitude, direction);
  }

  /**
   * Resets the game to its initial state.
   */
  @Override
  public void reset() {

  }

  public void advanceRound() {
    round++;
    collidables = loader.getCollidableContainer();
    collisionHandlers = rules.collisionHandlers();
  }

  public void advanceTurn() {
    turn = turnPolicy.getTurn();

  }

  public int getRound() {
    return round;
  }

  public int getTurn() {
    return turn;
  }

  public RulesRecord getRules() {
    return rules;
  }

  public List<PlayerRecord> getImmutablePlayers() {
    return playerContainer.getPlayerRecords();
  }

  public PlayerContainer getPlayerContainer() {
    return playerContainer;
  }

  public CollidableContainer getCollidableContainer() {
    return collidables;
  }

  public void endGame() {
    gameOver = true;
  }
}
