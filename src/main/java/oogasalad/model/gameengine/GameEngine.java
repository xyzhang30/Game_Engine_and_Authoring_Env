package oogasalad.model.gameengine;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import oogasalad.Pair;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.ExternalGameEngine;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.GameObjectContainer;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.player.PlayerContainer;
import oogasalad.model.gameparser.GameLoaderModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author Noah Loewy
 */
public class GameEngine implements ExternalGameEngine {

  private static final Logger LOGGER = LogManager.getLogger(GameEngine.class);
  private final GameLoaderModel loader;
  private final PlayerContainer playerContainer;
  private RulesRecord rules;
  private GameObjectContainer gameObjects;
  private Map<Pair, List<Command>> collisionHandlers;
  private int round;
  private int turn;
  private boolean gameOver;
  private boolean staticState;
  private Stack<GameRecord> staticStateStack;

  public GameEngine(String gameTitle) {
    loader = new GameLoaderModel(gameTitle);
    playerContainer = loader.getPlayerContainer();
    round = 1;
    startRound(loader);
  }

  /**
   * Starts the current game
   */
  private void startRound(GameLoaderModel loader) {
    gameOver = false;
    turn = 1; //first player ideally should have id 1
    staticState = true;
    playerContainer.setActive(turn);
    loader.makeLevel(round);
    gameObjects = loader.getGameObjectContainer();
    rules = loader.getRulesRecord();
    collisionHandlers = rules.collisionHandlers();
    playerContainer.getPlayer(1).updateActiveStrikeableId();
    gameObjects.setVisible(playerContainer.getPlayer(playerContainer.getActive()).getStrikeableID());
    playerContainer.startRound();
    gameObjects.addStaticStateGameObjects();
    playerContainer.addPlayerHistory();
    staticStateStack = new Stack<>();
    staticStateStack.push(
        new GameRecord(gameObjects.toGameObjectRecords(), playerContainer.getPlayerRecords(),
            round, turn, gameOver, staticState));
  }


  /**
   * Provides view with updated GameState as immutable record after each frame
   *
   * @return GameRecord object representing the current Game Objects, Scores, etc
   */
  @Override
  public GameRecord update(double dt) {
    gameObjects.update(dt);
    handleCollisions(dt); //private
    if (gameObjects.checkStatic()) {
      switchToCorrectStaticState(); //private
      updateHistory(); //private
      staticState = true;
    } else {
      staticState = false;
    }
    gameObjects.setVisible(playerContainer.getPlayer(playerContainer.getActive()).getStrikeableID());
    return new GameRecord(gameObjects.toGameObjectRecords(), playerContainer.getPlayerRecords(),
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
    gameObjects.getGameObject(id).applyInitialVelocity(magnitude,direction);
    rules.strikePolicy().getStrikePolicy().accept(id, this);
  }

  /**
   * Resets the game to its initial state.
   */
  @Override
  public void reset() {

  }

  public void advanceRound() {
    round++;
    playerContainer.applyDelayedScores();
    startRound(loader);
  }

  public void advanceTurn() {
    turn = rules.turnPolicy().getTurn();
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

  public GameObjectContainer getGameObjectContainer() {
    return gameObjects;
  }

  public void endGame() {
    gameOver = true;
  }

  public void toLastStaticState() {
    staticState = true;
    for(GameObjectRecord cr : gameObjects.toGameObjectRecords()) {
      GameObject c = gameObjects.getGameObject(cr.id());
      Optional<Scoreable> optionalScoreable = c.getScoreable();
      optionalScoreable.ifPresent(scoreable -> scoreable.setTemporaryScore(0));
    }
    GameRecord newCurrentState = staticStateStack.pop();
    turn = newCurrentState.turn();
    round = newCurrentState.round();
    gameOver = newCurrentState.gameOver();
    gameObjects.toLastStaticStateGameObjects();
    playerContainer.toLastStaticStateVariables();
  }

  private void handleCollisions(double dt) {
    Set<Pair> collisionPairs = gameObjects.getCollisionPairs();
    for (Pair collision : collisionPairs) {
      if (rules.physicsMap().containsKey(collision)) {
        rules.physicsMap().get(collision).handleCollision(gameObjects, dt);
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
    gameObjects.addStaticStateGameObjects();
    staticStateStack.push(
        new GameRecord(gameObjects.toGameObjectRecords(), playerContainer.getPlayerRecords(),
            round, turn, gameOver, staticState));
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

  public void setActivesStrikeablesInvisible() {
    int id = playerContainer.getPlayer(playerContainer.getActive()).getStrikeableID();
    gameObjects.getGameObject(id).setVisible(false);
  }
}
