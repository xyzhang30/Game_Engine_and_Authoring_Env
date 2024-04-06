package oogasalad.model.gameengine;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import oogasalad.Pair;
import oogasalad.model.api.CollidableRecord;
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

  private final GameLoaderModel loader;
  private Stack<GameRecord> staticStateStack;


  public GameEngine(String gameTitle) {
    loader = new GameLoaderModel(gameTitle);
    start(loader);
  }

  /**
   * Starts the current game
   */
  @Override
  public void start(GameLoaderModel loader) {
    gameOver = false;
    round = 1;
    turn = 1; //first player ideally should have id 1
    staticState = true;
    playerContainer = loader.getPlayerContainer();
    rules = loader.getRulesRecord();
    collidables = loader.getCollidableContainer();
    collisionHandlers = rules.collisionHandlers();
    turnPolicy = loader.getTurnPolicy();
    staticStateStack = new Stack<>();
    staticStateStack.push(
        new GameRecord(collidables.getCollidableRecords(), playerContainer.getPlayerRecords(),
            round, turn, gameOver, staticState));
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
    collidables.update(dt);
    handleCollisions(dt); //private
    if (collidables.checkStatic()) {
      updateHistory(); //private
      switchToCorrectStaticState(); //private
    }
    else {
      staticState = false;
    }
    return new GameRecord(collidables.getCollidableRecords(), playerContainer.getPlayerRecords(),
        round, turn, gameOver, staticState);
    }

  private void handleCollisions(double dt) {
    Set<Pair> collisionPairs = collidables.getCollisionPairs(); //to implement
    for(Pair collision : collisionPairs) {
      Collidable collidable1 = collidables.getCollidable(collision.getFirst());
      Collidable collidable2 = collidables.getCollidable(collision.getSecond());
      collidable1.onCollision(collidable2, dt);
      collidable2.onCollision(collidable1, dt);
      collidable1.updatePostCollisionVelocity();
      collidable2.updatePostCollisionVelocity();
      if(collisionHandlers.containsKey(collision)) {
      for (Command cmd : collisionHandlers.get(collision)) {
        cmd.execute(this);
      }
      }
    }
  }

  private void switchToCorrectStaticState() {
    if (rules.winCondition().execute(this) == 1.0) {
      endGame();
    }// else if (rules.roundCondition().execute(this) == 1.0) {
   //   advanceRound();
    //}
      else {
      for (Command cmd : rules.advance()) {
        cmd.execute(this);
      }
    }
  }

  private void updateHistory() {
    staticState = true;
    playerContainer.addStaticStateVariables();
    collidables.addStaticStateCollidables();
    staticStateStack.push(
        new GameRecord(collidables.getCollidableRecords(), playerContainer.getPlayerRecords(),
            round, turn, gameOver, staticState));
  }


  @Override
  public GameRecord handleCollisions(List<Pair> collisions, double dt) {
    return null;
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

  public void toLastStaticState() {
    staticState = true;
    GameRecord newCurrentState = staticStateStack.pop();
    turn = newCurrentState.turn();
    round = newCurrentState.round();
    gameOver = newCurrentState.gameOver();
    collidables.toLastStaticStateCollidables();
    playerContainer.toLastStaticStateVariables();
  }


}
