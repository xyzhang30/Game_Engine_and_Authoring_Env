package oogasalad.model.gameparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import oogasalad.Pair;
import oogasalad.model.api.data.CollidableObject;
import oogasalad.model.api.data.ParserPlayer;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.api.exception.InvalidFileException;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameengine.collidable.Controllable;
import oogasalad.model.gameengine.collidable.ownable.Ownable;
import oogasalad.model.gameengine.collidable.PhysicsHandler;
import oogasalad.model.gameengine.collidable.collision.FrictionHandler;
import oogasalad.model.gameengine.collidable.collision.MomentumHandler;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.condition.Condition;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.player.PlayerContainer;
import oogasalad.model.gameengine.statichandlers.GenericStaticStateHandler;
import oogasalad.model.gameengine.statichandlers.StaticStateHandlerLinkedListBuilder;
import oogasalad.model.gameengine.strike.StrikePolicy;
import oogasalad.model.gameengine.turn.TurnPolicy;
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
  private final Map<Pair, PhysicsHandler> physicsMap;
  private final List<Integer> movables;
  private List<Entry<BiPredicate<Integer, CollidableObject>,
      BiFunction<Integer, Integer, PhysicsHandler>>> conditionsList;
//  private Map<Integer, Player> collidablePlayerMap;

  private final GenericStaticStateHandler staticHandler;

  /**
   * Constructs a GameLoaderModel object with the specified ID.
   *
   * @param gameTitle The title of the game data to load.
   */
  public GameLoaderModel(String gameTitle) throws InvalidFileException {
    super(gameTitle);
    createPlayerContainer();
    movables = new ArrayList<>();
    physicsMap = new HashMap<>();

    staticHandler = StaticStateHandlerLinkedListBuilder.buildLinkedList(List.of(
        "GameOverStaticStateHandler",
        "RoundOverStaticStateHandler", "TurnOverStaticStateHandler"));
    createCollisionTypeMap();
  }

  public void createLevel() {

  }

  /**
   * Retrieves the player container.
   *
   * @return The player container.
   */
  public PlayerContainer getPlayerContainer() {
    return playerContainer;
  }


  public void makeLevel(int id) {
    createCollidableContainer();
    addPlayerControllables();
    createRulesRecord();
  }

  private void addPlayerControllables() {
    for (ParserPlayer parserPlayer : gameData.getPlayers()){
      int playerId = parserPlayer.playerId();
      List<Integer> playerControllableIds = parserPlayer.myCollidable();
      List<Controllable> playerControllableObjects = new ArrayList<>();
      for (int i : playerControllableIds){
        Optional<Controllable> optionalControllable = collidableContainer.getCollidable(i).getControllable();

        optionalControllable.ifPresent(playerControllableObjects::add);

      }
      playerContainer.getPlayer(playerId).addControllables(playerControllableObjects);

      List<Ownable> playerOwnableObjects = new ArrayList<>();
      for (int i : playerControllableIds){
        Optional<Ownable> optionalControllable = collidableContainer.getCollidable(i).getOwnable();
        optionalControllable.ifPresent(playerOwnableObjects::add);
      }
      playerContainer.getPlayer(playerId).addOwnables(playerOwnableObjects);
    }
//    for (PlayerRecord playerRecord : getPlayerContainer().getPlayerRecords()){
//      int controllableId = playerRecord.activeControllable();
//      getPlayerContainer().getPlayer(playerRecord.playerId()).addControllables(List.of(collidableContainer.getCollidable(controllableId).getControllable()));
//    }
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
//    collidablePlayerMap = new HashMap<>();
    Map<Integer, Player> playerMap = new HashMap<>();
    gameData.getPlayers().forEach(p -> {
      playerMap.put(p.playerId(), new Player(p.playerId()));
      Player player = new Player(p.playerId());
      playerMap.put(p.playerId(), player);
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
    StrikePolicy strikePolicy = createStrikePolicy();
    rulesRecord = new RulesRecord(commandMap,
        winCondition, roundPolicy, advanceTurnCmds, advanceRoundCmds, physicsMap, turnPolicy,
        staticHandler, strikePolicy);
  }

  private StrikePolicy createStrikePolicy() {
    System.out.println("gamedata strike: "+gameData.getRules().strikePolicy());
    return StrikePolicyFactory.createStrikePolicy(gameData.getRules().strikePolicy());
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
