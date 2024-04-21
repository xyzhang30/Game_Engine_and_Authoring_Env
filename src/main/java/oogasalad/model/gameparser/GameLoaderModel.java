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
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.api.data.ParserPlayer;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.api.exception.InvalidFileException;
import oogasalad.model.gameengine.checkstatic.StaticChecker;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.condition.Condition;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.GameObjectContainer;
import oogasalad.model.gameengine.gameobject.PhysicsHandler;
import oogasalad.model.gameengine.gameobject.Strikeable;
import oogasalad.model.gameengine.gameobject.collision.FrictionHandler;
import oogasalad.model.gameengine.gameobject.collision.MomentumHandler;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.player.PlayerContainer;
import oogasalad.model.gameengine.statichandlers.StaticStateHandler;
import oogasalad.model.gameengine.statichandlers.StaticStateHandlerLinkedListFactory;
import oogasalad.model.gameengine.strike.StrikePolicy;
import oogasalad.model.gameengine.turn.TurnPolicy;
import oogasalad.model.gameengine.rank.PlayerRecordComparator;
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
  private final Map<Pair, PhysicsHandler> physicsMap;
  private final List<Integer> collidables;
  private final StaticStateHandler staticHandler;
  private PlayerContainer playerContainer;
  private GameObjectContainer gameObjectContainer;
  private RulesRecord rulesRecord;
//  private Map<Integer, Player> collidablePlayerMap;
  private List<Entry<BiPredicate<Integer, GameObjectProperties>,
      BiFunction<Integer, Integer, PhysicsHandler>>> conditionsList;

  /**
   * Constructs a GameLoaderModel object with the specified ID.
   *
   * @param gameTitle The title of the game data to load.
   */
  public GameLoaderModel(String gameTitle) throws InvalidFileException {
    super(gameTitle);
    createPlayerContainer();
    collidables = new ArrayList<>();
    physicsMap = new HashMap<>();

    staticHandler = StaticStateHandlerLinkedListFactory.buildLinkedList(List.of(
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



  public void prepareRound(int id) {
    createGameObjectContainer();
    addPlayerStrikeables();
    createRulesRecord();
  }

  private void addPlayerStrikeables() {
    for (ParserPlayer parserPlayer : gameData.getPlayers()) {
      int playerId = parserPlayer.playerId();
      List<Integer> playerStrikeableIds = parserPlayer.myStrikeable();
      List<Strikeable> playerStrikeableObjects = new ArrayList<>();
      for (int i : playerStrikeableIds) {
        Optional<Strikeable> optionalStrikeable = gameObjectContainer.getGameObject(i)
            .getStrikeable();
        optionalStrikeable.ifPresent(playerStrikeableObjects::add);

      }
      playerContainer.getPlayer(playerId).addStrikeables(playerStrikeableObjects);

      List<Scoreable> playerScoreableObjects = new ArrayList<>();
      for (int i : playerStrikeableIds) {
        Optional<Scoreable> optionalStrikeable = gameObjectContainer.getGameObject(i)
            .getScoreable();
        optionalStrikeable.ifPresent(playerScoreableObjects::add);
      }
      playerContainer.getPlayer(playerId).addScoreables(playerScoreableObjects);
    }
  }

  /**
   * Retrieves the collidable container.
   *
   * @return The collidable container.
   */
  public GameObjectContainer getGameObjectContainer() {
    return gameObjectContainer;
  }

  /**
   * Retrieves the rules record.
   *
   * @return The rules record.
   */
  public RulesRecord getRulesRecord() {

    return rulesRecord;
  }

  private void createGameObjectContainer() {
    Map<Integer, GameObject> gameObjects = new HashMap<>();
    gameData.getGameObjects().forEach(co -> {
      if (co.properties().contains("collidable")) {
        this.collidables.add(co.collidableId());
      }
      gameObjects.put(co.collidableId(), createCollidable(co));
      gameObjects.keySet().forEach(id -> addPairToPhysicsMap(co, id, conditionsList));
    });

    this.gameObjectContainer = new GameObjectContainer(gameObjects);
  }

  private void addPairToPhysicsMap(GameObjectProperties co, Integer id,
      List<Entry<BiPredicate<Integer, GameObjectProperties>, BiFunction<Integer, Integer, PhysicsHandler>>> conditionsList) {
    for (Entry<BiPredicate<Integer, GameObjectProperties>, BiFunction<Integer, Integer, PhysicsHandler>> entry : conditionsList) {
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
        Map.entry((key, co) -> collidables.contains(key) && co.properties().contains("collidable"),
            MomentumHandler::new));
    conditionsList.add(
        Map.entry((key, co) -> collidables.contains(key) || co.properties().contains("collidable"),
            FrictionHandler::new));
  }

  private GameObject createCollidable(GameObjectProperties co) {
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
    System.out.println("advanceTurnCmds");
    List<Command> advanceTurnCmds = createAdvanceCommands(gameData.getRules().advanceTurn());
    System.out.println("Advance Rounds:");
    List<Command> advanceRoundCmds = createAdvanceCommands(gameData.getRules().advanceRound());
    Condition winCondition = createCondition(gameData.getRules().winCondition());
    Condition roundPolicy = createCondition(gameData.getRules().roundPolicy());
    TurnPolicy turnPolicy = createTurnPolicy();
    StrikePolicy strikePolicy = createStrikePolicy();
    PlayerRecordComparator comp = createRankComparator();
    StaticChecker checker = createStaticChecker(gameData.getRules().staticChecker());
    System.out.println(checker);
    rulesRecord = new RulesRecord(commandMap,
        winCondition, roundPolicy, advanceTurnCmds, advanceRoundCmds, physicsMap, turnPolicy,
        staticHandler, strikePolicy, comp, checker);
  }

//  private StaticChecker createStaticChecker() {
//    return StaticCheckerFactory.createStaticChecker(gameData.getRules().staticCheckerType(),
//        gameData.getRules().staticCheckerParams());
//
//  }

  private StaticChecker createStaticChecker(Map<String, List<Integer>> staticChecker) {
    if (staticChecker.keySet().iterator().hasNext()) {
      String checkerName = staticChecker.keySet().iterator().next();
      return StaticCheckerFactory.createStaticChecker(checkerName, staticChecker.get(checkerName));
    } else {
      throw new InvalidCommandException("");
    }
  }


  private PlayerRecordComparator createRankComparator() {
    return PlayerRankComparatorFactory.createRankComparator(gameData.getRules().rankComparator());
  }

  private StrikePolicy createStrikePolicy() {
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

  private List<Command> createAdvanceCommands(Map<String, List<Double>> commands) {
    List<Command> ret = new ArrayList<>();
    commands.keySet().forEach(command -> {
      ret.add(CommandFactory.createCommand(command, commands.get(command)));
      System.out.println("command:"+command + " "+ commands.get(command));
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
