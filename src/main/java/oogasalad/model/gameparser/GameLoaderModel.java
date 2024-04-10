package oogasalad.model.gameparser;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.api.exception.InvalidFileException;
import oogasalad.model.gameengine.Player;
import oogasalad.model.gameengine.PlayerContainer;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.condition.Condition;
import oogasalad.model.gameengine.turn.TurnPolicy;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameengine.collidable.collision.FrictionHandler;
import oogasalad.model.gameengine.collidable.collision.MomentumHandler;
import oogasalad.model.gameengine.collidable.PhysicsHandler;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.api.data.CollidableObject;
import oogasalad.model.api.data.CollisionRule;
import oogasalad.model.api.data.ParserPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Concrete implementation of GameLoader for passing game data necessary for the Model.
 *
 * @author Judy He, Alisha Zhang
 */
public class GameLoaderModel extends GameLoader {

  private static final Logger LOGGER = LogManager.getLogger(GameLoaderModel.class);

  public static final String BASE_PATH = "oogasalad.model.gameengine.";
  private static final String CONDITION_PATH = "condition.";
  private static final String TURN_POLICY_PATH = "turn.";
  private PlayerContainer playerContainer;
  private CollidableContainer collidableContainer;
  private RulesRecord rulesRecord;
  private Map<Pair, PhysicsHandler> physicsMap;
  private TurnPolicy turnPolicy;


  /**
   * Constructs a GameLoaderModel object with the specified ID.
   *
   * @param gameTitle The title of the game data to load.
   */
  public GameLoaderModel(String gameTitle) throws InvalidFileException {
    super(gameTitle);
    this.createCollidableContainer();
    this.createPlayerContainer();
    this.createTurnPolicy();
    this.createRulesRecord();

  }

  // alisha
  protected void createPlayerContainer() {
    Map<Integer, Player> playerMap = new HashMap<>();
    for (ParserPlayer p : gameData.getPlayers()) {
      Player player = new Player(p.playerId(), getCollidableContainer().getCollidable(p.myCollidable()));
      playerMap.put(p.playerId(), player);
    }
    this.playerContainer = new PlayerContainer(playerMap);
  }

  /**
   * Retrieves the player container.
   *
   * @return The player container.
   */
  public PlayerContainer getPlayerContainer() {
    return playerContainer;
  }

  protected void createCollidableContainer() {
    List<CollidableObject> collidableObjects = gameData.getCollidableObjects();
    List<Integer> moveables = new ArrayList<>();
    Map<Integer, Collidable> collidables = new HashMap<>();
    physicsMap = new HashMap<>();
    for (CollidableObject co : collidableObjects) {
      if (co.properties().contains("movable")) {
        moveables.add(co.collidableId());
      }
      for (Integer key : collidables.keySet()) {
        if(moveables.contains(key) && co.properties().contains("movable")) {
          physicsMap.put(new Pair(key, co.collidableId()), new MomentumHandler(key,
              co.collidableId()));
        }
        else if (moveables.contains(key) || co.properties().contains("movable")){
          physicsMap.put(new Pair(key, co.collidableId()), new FrictionHandler(key,
              co.collidableId()));
        }
      }

      collidables.put(co.collidableId(), createCollidable(co));
    }
    this.collidableContainer = new CollidableContainer(collidables);
  }

  protected Collidable createCollidable(CollidableObject co) {
    return new Collidable(
        co.collidableId(),
        co.mass(),
        co.position().xPosition(),
        co.position().yPosition(),
        co.properties().contains("visible"),
        co.friction(),
        co.dimension().xDimension(),
        co.dimension().yDimension(),
        co.shape());
  }


  /**
   * Retrieves the collidable container.
   *
   * @return The collidable container.
   */
  public CollidableContainer getCollidableContainer() {
    return collidableContainer;
  }

  // alisha
  protected void createRulesRecord() {
      Map<Pair, List<Command>> commandMap = new HashMap<>();
      for (CollisionRule rule : gameData.getRules().collisions()) {
        Pair pair = new Pair(rule.firstId(),
            rule.secondId()); //collision rule is the one with ids and command map
        List<Command> commands = new ArrayList<>();
        for (Map<String, List<Double>> commandsToParams : rule.command()) { //looping through the
          for (String s : commandsToParams.keySet()) {
            commands.add(CommandFactory.createCommand(s, commandsToParams.get(s)));
          }
        }
        commandMap.put(pair, commands);
      }

      Class<?> cc = null;

      //advance turn commands
      List<Command> advanceTurnCmds = new ArrayList<>();
      for (Map<String, List<Double>> commandsToParams : gameData.getRules().advanceTurn()) {
        for (String s : commandsToParams.keySet()) {
          advanceTurnCmds.add(CommandFactory.createCommand(s, commandsToParams.get(s)));
        }
      }

      //advance round commands
      List<Command> advanceRoundCmds = new ArrayList<>();
      for (Map<String, List<Double>> commandsToParams : gameData.getRules().advanceRound()) {
        for (String s : commandsToParams.keySet()) {
          advanceRoundCmds.add(CommandFactory.createCommand(s, commandsToParams.get(s)));
        }
      }

      Condition winCondition = null;
      if(gameData.getRules().winCondition().keySet().iterator().hasNext()) {
        String condition = gameData.getRules().winCondition().keySet().iterator().next();
        winCondition = ConditionFactory.createCondition(condition,
            gameData.getRules().winCondition().get(condition));
      }
      else {
        throw new InvalidCommandException("");
      }

      Condition roundPolicy = null;
      if(gameData.getRules().roundPolicy().keySet().iterator().hasNext()) {
        String condition = gameData.getRules().roundPolicy().keySet().iterator().next();
        roundPolicy = ConditionFactory.createCondition(condition,
            gameData.getRules().roundPolicy().get(condition));
      }
      else {
        throw new InvalidCommandException("");
      }
      //round condition command

      rulesRecord = new RulesRecord(commandMap,
          winCondition, roundPolicy, advanceTurnCmds, advanceRoundCmds, physicsMap);

    }


  public TurnPolicy getTurnPolicy() {
    return turnPolicy;
  }

  protected void createTurnPolicy() {
    try {
      Class<?> cc = Class.forName(BASE_PATH + TURN_POLICY_PATH + gameData.getRules().turnPolicy());
      turnPolicy = (TurnPolicy) cc.getDeclaredConstructor(PlayerContainer.class)
          .newInstance(this.playerContainer);
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
             ClassNotFoundException | InvocationTargetException e) {
      LOGGER.error("invalid command, " + e.getMessage());
      throw new InvalidCommandException(e.getMessage());
    }
  }


  /**
   * Retrieves the rules record.
   *
   * @return The rules record.
   */
  public RulesRecord getRulesRecord() {
    return rulesRecord;
  }
}
