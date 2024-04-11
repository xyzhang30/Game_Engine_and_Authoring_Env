package oogasalad.model.gameparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import oogasalad.Pair;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.api.exception.InvalidFileException;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.player.PlayerContainer;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.condition.Condition;
import oogasalad.model.gameengine.statichandlers.GenericStaticStateHandler;
import oogasalad.model.gameengine.statichandlers.StaticStateHandlerLinkedListBuilder;
import oogasalad.model.gameengine.turn.TurnPolicy;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameengine.collidable.collision.FrictionHandler;
import oogasalad.model.gameengine.collidable.collision.MomentumHandler;
import oogasalad.model.gameengine.collidable.PhysicsHandler;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.api.data.CollidableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Concrete implementation of GameLoader for passing game data necessary for the Model.
 *
 * @author Judy He, Alisha Zhang
 */
public class GameLoaderModel extends GameLoader {

  protected static final String BASE_PATH = "oogasalad.model.gameengine.";
  private static final Logger LOGGER = LogManager.getLogger(GameLoaderModel.class);
  private PlayerContainer playerContainer;
  private CollidableContainer collidableContainer;
  private RulesRecord rulesRecord;
  private Map<Pair, PhysicsHandler> physicsMap;
  private List<Integer> movables;
  private List<Entry<BiPredicate<Integer, CollidableObject>,
      BiFunction<Integer, Integer, PhysicsHandler>>> conditionsList;

  private GenericStaticStateHandler staticHandler;


  /**
   * Constructs a GameLoaderModel object with the specified ID.
   *
   * @param gameTitle The title of the game data to load.
   */
  public GameLoaderModel(String gameTitle) throws InvalidFileException {
    super(gameTitle);
    movables = new ArrayList<>();
    physicsMap = new HashMap<>();
    staticHandler = StaticStateHandlerLinkedListBuilder.buildLinkedList(List.of(
        "GameOverStaticStateHandler",
        "RoundOverStaticStateHandler", "TurnOverStaticStateHandler"));

    createCollisionTypeMap();
    createCollidableContainer();
    createPlayerContainer();
    createRulesRecord();
    StaticStateHandlerLinkedListBuilder builder = new StaticStateHandlerLinkedListBuilder();


  }

  /**
   * Retrieves the player container.
   *
   * @return The player container.
   */
  public PlayerContainer getPlayerContainer() {
    return playerContainer;
  }


  /**
   * Retrieves the collidable container.
   *
   * @return The collidable container.
   */
  public CollidableContainer getCollidableContainer() {
    return collidableContainer;
  }

  /**
   * Retrieves the rules record.
   *
   * @return The rules record.
   */
  public RulesRecord getRulesRecord() {
    return rulesRecord;
  }

  private void createCollidableContainer() {
    Map<Integer, Collidable> collidables = new HashMap<>();
    gameData.getCollidableObjects().forEach(co -> {
      if (co.properties().contains("movable")) {
        movables.add(co.collidableId());
      }
      collidables.put(co.collidableId(), createCollidable(co));
      collidables.keySet().forEach(id -> addPairToPhysicsMap(co, id, conditionsList));
    });

    this.collidableContainer = new CollidableContainer(collidables);
  }

  private void addPairToPhysicsMap(CollidableObject co, Integer id,
      List<Entry<BiPredicate<Integer, CollidableObject>, BiFunction<Integer, Integer, PhysicsHandler>>> conditionsList) {
    for (Entry<BiPredicate<Integer, CollidableObject>, BiFunction<Integer, Integer, PhysicsHandler>> entry : conditionsList) {
      if (entry.getKey().test(id, co) && id != co.collidableId()) {
        physicsMap.put(new Pair(id, co.collidableId()), entry.getValue().apply(id,
            co.collidableId()));
        break;
      }
    }
  }

  private void createCollisionTypeMap() {
    conditionsList = new ArrayList<>();
    conditionsList.add(
        Map.entry((key, co) -> movables.contains(key) && co.properties().contains("movable"),
            MomentumHandler::new));
    conditionsList.add(
        Map.entry((key, co) -> movables.contains(key) || co.properties().contains("movable"),
            FrictionHandler::new));
  }

  private Collidable createCollidable(CollidableObject co) {
    return CollidableFactory.createCollidable(co);
  }

  private void createPlayerContainer() {
    Map<Integer, Player> playerMap = new HashMap<>();
    gameData.getPlayers().forEach(p -> {
      playerMap.put(p.playerId(), new Player(p.playerId(), p.myCollidable()));
    });
    this.playerContainer = new PlayerContainer(playerMap);
  }

  private void createRulesRecord() {
    Map<Pair, List<Command>> commandMap = createCommandMap();
    List<Command> advanceTurnCmds = createAdvanceCommands(gameData.getRules().advanceTurn());
    List<Command> advanceRoundCmds = createAdvanceCommands(gameData.getRules().advanceRound());
    Condition winCondition = createCondition(gameData.getRules().winCondition());
    Condition roundPolicy = createCondition(gameData.getRules().roundPolicy());
    TurnPolicy turnPolicy = createTurnPolicy();
    rulesRecord = new RulesRecord(commandMap,
        winCondition, roundPolicy, advanceTurnCmds, advanceRoundCmds, physicsMap, turnPolicy,
        staticHandler);
  }

  private TurnPolicy createTurnPolicy() {
    return TurnPolicyFactory.createTurnPolicy(gameData.getRules().turnPolicy(),
        playerContainer);
  }

  private Condition createCondition(Map<String, List<Double>> conditionToParams) {
    if (conditionToParams.keySet().iterator().hasNext()) {
      String conditionName = conditionToParams.keySet().iterator().next();
      return ConditionFactory.createCondition(conditionName, conditionToParams.get(conditionName));
    } else {
      throw new InvalidCommandException("");
    }
  }

  private List<Command> createAdvanceCommands(List<Map<String, List<Double>>> commands) {
    List<Command> ret = new ArrayList<>();
    commands.forEach(commandsToParams -> {
      commandsToParams.keySet().forEach(s -> {
        ret.add(CommandFactory.createCommand(s, commandsToParams.get(s)));
      });
    });
    return ret;
  }

  private Map<Pair, List<Command>> createCommandMap() {
    Map<Pair, List<Command>> commandMap = new HashMap<>();
    gameData.getRules().collisions().forEach(rule -> {
      List<Command> commands = new ArrayList<>();
      rule.command().forEach(commandsToParams -> {
        commandsToParams.keySet().forEach(s -> {
          commands.add(CommandFactory.createCommand(s, commandsToParams.get(s)));
        });
      });
      commandMap.put(new Pair(rule.firstId(), rule.secondId()), commands);
    });
    return commandMap;
  }

}
