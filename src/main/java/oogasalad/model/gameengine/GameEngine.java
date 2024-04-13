package oogasalad.model.gameengine;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import oogasalad.Pair;
import oogasalad.model.api.ExternalGameEngine;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.player.PlayerContainer;
import oogasalad.model.gameengine.statichandlers.GenericStaticStateHandler;
import oogasalad.model.gameparser.GameLoaderModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author Noah Loewy
 */
public class GameEngine implements ExternalGameEngine {

  private static final Logger LOGGER = LogManager.getLogger(GameEngine.class);
  private final GameLoaderModel loader;
  private PlayerContainer playerContainer;
  private RulesRecord rules;
  private CollidableContainer collidables;
  private Map<Pair, List<Command>> collisionHandlers;
  private int round;
  private int turn;
  private boolean gameOver;
  private boolean staticState;
  private Stack<GameRecord> staticStateStack;

  public GameEngine(String gameTitle) {
    loader = new GameLoaderModel(gameTitle);
    playerContainer = loader.getPlayerContainer();
    rules = loader.getRulesRecord();
    round = 1;
    start(loader);
  }

  /**
   * Starts the current game
   */
  @Override
  public void start(GameLoaderModel loader) {
    gameOver = false;
    turn = 1; //first player ideally should have id 1
    staticState = true;
    playerContainer.startRound();
    collidables = loader.getCollidableContainer();
    collisionHandlers = rules.collisionHandlers();
    collidables.addStaticStateCollidables();
    playerContainer.addPlayerHistory();
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
      switchToCorrectStaticState(); //private
      updateHistory(); //private
      staticState = true;
    } else {
      staticState = false;
    }
    return new GameRecord(collidables.getCollidableRecords(), playerContainer.getPlayerRecords(),
        round, turn, gameOver, staticState);
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
    LOGGER.info(" player " + turn + " apply initial velocity with magnitude " + magnitude + " and "
        + "direction "
        + direction * 180 / Math.PI);
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
    start(loader);
    System.out.println(getGameRecord());
  }

  public void advanceTurn() {
    turn = rules.turnPolicy().getTurn();
    while (playerContainer.getPlayer(turn).isRoundCompleted()) {
      turn = rules.turnPolicy().getTurn();
    }
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
    //System.out.println(newCurrentState);
    turn = newCurrentState.turn();
    round = newCurrentState.round();
    gameOver = newCurrentState.gameOver();
    collidables.toLastStaticStateCollidables();
    playerContainer.toLastStaticStateVariables();
  }

  private void handleCollisions(double dt) {
    Set<Pair> collisionPairs = collidables.getCollisionPairs();
    System.out.println(collisionPairs);
    for (Pair collision : collisionPairs) {
      if (rules.physicsMap().containsKey(collision)) {
        rules.physicsMap().get(collision).handleCollision(collidables, dt);
      }
      if (collisionHandlers.containsKey(collision)) {
        for (Command cmd : collisionHandlers.get(collision)) {
          LOGGER.info(
              toLogForm(cmd) + " " + "(collision " + "info" + " - ) " + collision.getFirst() + " "
                  + collision.getSecond());
          cmd.execute(this);
        }
      }
    }
    if (rules.winCondition().evaluate(this)) {
      endGame();
    }
  }

  private void switchToCorrectStaticState() {
    rules.staticStateHandler().handle(this, rules);
  }


  private void updateHistory() {
    staticState = true;
    playerContainer.addPlayerHistory();
    collidables.addStaticStateCollidables();
    staticStateStack.push(
        new GameRecord(collidables.getCollidableRecords(), playerContainer.getPlayerRecords(),
            round, turn, gameOver, staticState));
   // printStaticStateStack();
  }

  private void printStaticStateStack() {
    for(GameRecord r: staticStateStack) {
      System.out.print(r.players().get(0).score() + " ");
    }
  }


  public boolean isOver() {
    return gameOver;
  }


  private String toLogForm(Object o) {
    return o.toString().substring(o.toString().lastIndexOf(".") + 1,
        o.toString().lastIndexOf("@"));
  }

  public GameRecord getGameRecord() {
    return staticStateStack.peek();
  }

}
