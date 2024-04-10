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

  private static final String COMMAND_PATH = "oogasalad.model.gameengine.command.";
  private static final String TURN_POLICY_PATH = "oogasalad.model.gameengine.turn.";
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
    try {
      Map<Pair, List<Command>> commandMap = new HashMap<>();
      int maxRounds = gameData.getVariables().get(0).global().maxRounds();
      int maxTurns = gameData.getVariables().get(0).global().maxTurns();

      //on collision rules
      for (CollisionRule rule : gameData.getRules().collisions()) {
        Pair pair = new Pair(rule.firstId(),
            rule.secondId()); //collision rule is the one with ids and command map
        List<Command> commands = new ArrayList<>();
        for (Map<String, List<Double>> command : rule.command()) { //looping through the list of command maps
          for (String s : command.keySet()) { //this is a for loop but there's always only going to be 1 command in the map (probably should change the structure of the json afterward)
            Class<?> cc = Class.forName(COMMAND_PATH + s);
            commands.add(
                (Command) cc.getDeclaredConstructor(List.class).newInstance(command.get(s)));
            commandMap.put(pair, commands);
          }
        }
      }

      Class<?> cc = null;

      //advance turn commands
      List<Command> advanceTurnCmds = new ArrayList<>();
      for (Map<String, List<Double>> condition : gameData.getRules().advanceTurn()) {
        for (String s : condition.keySet()) {
          cc = Class.forName(COMMAND_PATH + s);
          advanceTurnCmds.add(
              (Command) cc.getDeclaredConstructor(List.class).newInstance(condition.get(s)));
        }

      }

      //advance round commands
      List<Command> advanceRoundCmds = new ArrayList<>();
      for (Map<String, List<Double>> condition : gameData.getRules().advanceRound()) {
        for (String s : condition.keySet()) {
          cc = Class.forName(COMMAND_PATH + s);
          advanceRoundCmds.add(
              (Command) cc.getDeclaredConstructor(List.class).newInstance(condition.get(s)));
        }

      }

      //win condition command
      List<Double> params = new ArrayList<>();
      for (String condition : gameData.getRules().winCondition().keySet()) {
        cc = Class.forName(COMMAND_PATH + condition);
        params = gameData.getRules().winCondition().get(condition);
      }
      assert cc != null;
      Command winCondition = (Command) cc.getDeclaredConstructor(List.class).newInstance(params);

      //round condition command
      List<Command> roundPolicy = new ArrayList<>();
      for (String condition : gameData.getRules().roundPolicy().keySet()) {
        cc = Class.forName(COMMAND_PATH + condition);
        params = gameData.getRules().winCondition().get(condition);
        roundPolicy.add((Command) cc.getDeclaredConstructor(List.class).newInstance(gameData.getRules().roundPolicy().get(condition)));
      }
      Command roundPolicyCommand = roundPolicy.get(0);

      rulesRecord = new RulesRecord(maxRounds, maxTurns, commandMap,
          winCondition, roundPolicyCommand, advanceTurnCmds, advanceRoundCmds, physicsMap);

    } catch (AssertionError | NoSuchMethodException | IllegalAccessException |
             InstantiationException |
             ClassNotFoundException | InvocationTargetException e) {
      LOGGER.error("invalid command, "+e.getMessage());
      throw new InvalidCommandException(e.getMessage());
    }
  }

  public TurnPolicy getTurnPolicy() {
    return turnPolicy;
  }

  protected void createTurnPolicy() {
    try {
      Class<?> cc = Class.forName(TURN_POLICY_PATH + gameData.getRules().turnPolicy());
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

