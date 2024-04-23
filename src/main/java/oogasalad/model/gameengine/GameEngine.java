package oogasalad.model.gameengine;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import oogasalad.Pair;
import oogasalad.model.api.ExternalGameEngine;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.checkstatic.StaticChecker;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.gameobject.CollisionDetector;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.GameObjectContainer;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.player.PlayerContainer;
import oogasalad.model.gameparser.GameLoaderModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The GameEngine class represents the core engine of a game, managing the game's state,
 * progression, and interactions between game objects and players. It handles game updates, round
 * and turn advancements, collision detection, game over conditions, and restoration of game state
 * to previous static states.
 *
 * <p>The engine is responsible for orchestrating the game's logic, including updating game
 * objects, handling collisions, managing player turns and rounds, and tracking the game's
 * progress.
 *
 * @author Noah Loewy
 */

public class GameEngine implements ExternalGameEngine {

  private static final Logger LOGGER = LogManager.getLogger(GameEngine.class);
  private final GameLoaderModel loader;
  private final PlayerContainer playerContainer;
  private RulesRecord rules;
  private GameObjectContainer gameObjects;
  private int round;
  private int turn;
  private boolean gameOver;
  private boolean staticState;
  private Stack<GameRecord> staticStateStack;
  private CollisionDetector collisionDetector;

  /**
   * Initializes a new GameEngine instance for the specified game title.
   *
   * @param gameTitle The title of the game.
   */

  public GameEngine(String gameTitle) {
    loader = new GameLoaderModel(gameTitle);
    playerContainer = loader.getPlayerContainer();
    round = 1;
    collisionDetector = new CollisionDetector();
    startRound(loader);
  }


  /**
   * Represents a singular timestep in the game. Each time step, it calls functions that modify the
   * GameObjects, Players, History, and GameEngine states, based on the placement of the GameObjects
   * and their interactions with the user and each other
   *
   * @return GameRecord object representing the current Game Objects, Scores, etc
   */

  @Override
  public GameRecord update(double dt) {
    gameObjects.getGameObjects().forEach(go -> {
      go.move(dt);
      go.update();
    });
    handleCollisions(dt);
    if (checkStatic(rules.checker())) {
      switchToCorrectStaticState();
      updateHistory();
      staticState = true;
    } else {
      staticState = false;
    }
    gameObjects.getGameObject(playerContainer.getPlayer(playerContainer.getActive()).getStrikeableID()).setVisible(true);
    return new GameRecord(getListOfGameObjectRecords(),
         getPlayerRecords(),
        round, turn, gameOver, staticState);
  }

  /**
   * Applies a velocity to the GameObject with the provided ID.
   *
   * @param magnitude The magnitude of the force to apply.
   * @param direction The direction of the force to apply.
   * @param id        The ID of the GameObject to apply the force to.
   */
  @Override
  public void applyInitialVelocity(double magnitude, double direction, int id) {
    LOGGER.info(" player " + turn + " apply initial velocity to GameObject " + id + " with "
        + "magnitude " + magnitude + "and direction " + direction * 180 / Math.PI);
    gameObjects.getGameObject(id).applyInitialVelocity(magnitude, direction);
    rules.strikePolicy().getStrikePolicy().accept(id, this);
  }

  /**
   * Resets the game to its initial state.
   */
  @Override
  public void reset() {

  }

  @Override
  public void moveActiveControllableX(boolean positive) {
    playerContainer.getPlayer(playerContainer.getActive()).getControllable().moveX(positive);
  }
  @Override
  public void moveActiveControllableY(boolean positive) {
    playerContainer.getPlayer(playerContainer.getActive()).getControllable().moveY(positive);
  }

/**
   * Advances the game to the next round by incrementing the round number, applying delayed scores
   * to players, and starting a new round.
   */
  public void advanceRound() {
    round++;
    playerContainer.getPlayers().forEach(Player::applyDelayedScore);
    startRound(loader);
  }

  /**
   * Advances the game to the next turn by updating the turn number based on the turn policy rules.
   */
  public void advanceTurn() {
    turn = rules.turnPolicy().getNextTurn();
    gameObjects.getGameObjects().forEach(GameObject::stop);
  }

  /**
   * Retrieves the container that holds information about players in the game.
   *
   * @return The PlayerContainer object.
   */

  public PlayerContainer getPlayerContainer() {
    return playerContainer;
  }

  /**
   * Retrieves the container that holds information about game objects in the game.
   *
   * @return The GameObjectContainer object.
   */

  public GameObjectContainer getGameObjectContainer() {
    return gameObjects;
  }

  /**
   * Ends the game, setting the game over flag to true.
   */

  public void endGame() {
    gameOver = true;
  }

  /**
   * Restores the game to its last static state by updating the game state based on the last
   * recorded static state stored in the staticStateStack.
   */

  public void toLastStaticState() {
    staticState = true;
    for (GameObjectRecord cr : getListOfGameObjectRecords()) {
      GameObject c = gameObjects.getGameObject(cr.id());
      Optional<Scoreable> optionalScoreable = c.getScoreable();
      optionalScoreable.ifPresent(scoreable -> scoreable.setTemporaryScore(0));
    }
    GameRecord newCurrentState = staticStateStack.peek();
    turn = newCurrentState.turn();
    round = newCurrentState.round();
    gameOver = newCurrentState.gameOver();
    gameObjects.getGameObjects().forEach(GameObject::toLastStaticStateGameObjects);
    playerContainer.getPlayers().forEach(Player::toLastStaticStatePlayers);
  }

  /**
   * Retrieves the last recorded static game record.
   *
   * @return The last recorded GameRecord representing the static game state.
   */
  public GameRecord restoreLastStaticGameRecord() {
    return staticStateStack.peek();
  }


  //obtains all the pairs of Game Objects colliding, and executes the respective physics
  // operations and commands caused by the collision
  private void handleCollisions(double dt) {
    Set<Pair> collisionPairs = getCollisionPairs();
    for (Pair collision : collisionPairs) {
      if (rules.physicsMap().containsKey(collision)) {
        rules.physicsMap().get(collision).handleCollision(gameObjects, dt);
      }
      if (rules.collisionHandlers().containsKey(collision)) {
        for (Command cmd : rules.collisionHandlers().get(collision)) {
          LOGGER.info(cmd.getClass().getSimpleName() + " " + "(collision " + "info" + " - ) "
              + collision.getFirst() + " " + collision.getSecond());
          cmd.execute(this);
        }
      }
    }
  }


  /**
   * //TODO JavaDoc
   *
   * @author Konur Nordberg
   */
  private Set<Pair> getCollisionPairs() {
    Set<Pair> collisionPairs = new HashSet<>();
    List<GameObjectRecord> records = getListOfGameObjectRecords();
    for (int i = 0; i < records.size(); i++) {
      GameObjectRecord record1 = records.get(i);
      GameObject gameObject1 = gameObjects.getGameObject(record1.id());
      for (int j = i + 1; j < records.size(); j++) {
        GameObjectRecord record2 = records.get(j);
        GameObject gameObject2 = gameObjects.getGameObject(record2.id());
        if (gameObject2.getVisible() && gameObject1.getVisible() && collisionDetector.isColliding(
            gameObject1, gameObject2)) {
          collisionPairs.add(new Pair(gameObject1, gameObject2));
        }
      }
    }
    return collisionPairs;
  }

  //starts the current round, by requesting the necessary information for that round from the
  // loader.
  private void startRound(GameLoaderModel loader) {
    System.out.println(loader);
    gameOver = false;
    turn = 1; //first player ideally should have id 1
    staticState = true;
    playerContainer.setActive(turn);
    loadRoundSpecificInformation(loader);
    playerContainer.getPlayer(1).updateActiveStrikeable();
    gameObjects.getGameObject(
        playerContainer.getPlayer(playerContainer.getActive()).getStrikeableID()).setVisible(true);
    playerContainer.getPlayers().forEach(Player::startRound);
    addInitialStaticStateToHistory();
  }

  //gets game objects, and rules for the game objects, for a specific round from game loader
  private void loadRoundSpecificInformation(GameLoaderModel loader) {
    loader.prepareRound(round);
    gameObjects = loader.getGameObjectContainer();
    rules = loader.getRulesRecord();
  }

  //adds the initial state of the game (before the round starts) to the game history
  private void addInitialStaticStateToHistory() {
    gameObjects.getGameObjects().forEach(GameObject::addStaticStateGameObject);
    playerContainer.getPlayers().forEach(Player::addPlayerHistory);
    staticStateStack = new Stack<>();
    staticStateStack.push(
        new GameRecord(getListOfGameObjectRecords(), getPlayerRecords().stream()
            .sorted(rules.rank())
            .collect(Collectors.toList()),
            round, turn, gameOver, staticState));
  }


  //calls static state handler to update engine based on the type of static state, using chain of
  // responsibility pattern
  private void switchToCorrectStaticState() {
    rules.staticStateHandler().handle(this, rules);
  }

  //updates the staticStateStack with the respective game record from the new static state. Also
  // calls functions to update the player-specific and GameObject-specific stacks
  private void updateHistory() {
    staticState = true;
    playerContainer.getPlayers().forEach(Player::addPlayerHistory);
    gameObjects.getGameObjects().forEach(GameObject::addStaticStateGameObject);
    staticStateStack.push(
        new GameRecord(getListOfGameObjectRecords(),
            getPlayerRecords().stream()
                .sorted(rules.rank())
                .collect(Collectors.toList()),
            round, turn, gameOver, staticState));
  }

   // Checks if all visible GameObjects in the container are meeting at least one of the static
   // conditions (defined for that game)
   private boolean checkStatic(List<StaticChecker> staticCheckers) {
     return staticCheckers.stream().anyMatch(checker ->
         gameObjects.getGameObjects().stream().allMatch(gameObject ->
             checker.isStatic(gameObject.toGameObjectRecord())
         )
     );
   }

   private List<GameObjectRecord> getListOfGameObjectRecords() {
     return gameObjects.getGameObjects().stream().map(GameObject::toGameObjectRecord)
         .collect(Collectors.toList());
   }

   private List<PlayerRecord> getPlayerRecords() {
     return playerContainer.getPlayers().stream()
         .map(Player::getPlayerRecord)
         .sorted(rules.rank())
         .collect(Collectors.toList());
   }

}
