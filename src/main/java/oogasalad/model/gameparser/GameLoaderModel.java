package oogasalad.model.gameparser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import oogasalad.model.Pair;
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.api.data.ParserPlayer;
import oogasalad.model.api.data.Rules;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.api.exception.InvalidFileException;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.checkstatic.StaticChecker;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.condition.Condition;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.PhysicsHandler;
import oogasalad.model.gameengine.gameobject.Strikeable;
import oogasalad.model.gameengine.gameobject.collision.FrictionHandler;
import oogasalad.model.gameengine.gameobject.collision.MomentumHandler;
import oogasalad.model.gameengine.gameobject.controllable.Controllable;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.player.PlayerContainer;
import oogasalad.model.gameengine.rank.PlayerRecordComparator;
import oogasalad.model.gameengine.statichandlers.StaticStateHandler;
import oogasalad.model.gameengine.statichandlers.StaticStateHandlerLinkedListFactory;
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
  private final Map<Pair, PhysicsHandler> physicsMap;
  private final List<Integer> collidables;
  private final StaticStateHandler staticHandler;
  private PlayerContainer playerContainer;
  private RulesRecord rulesRecord;
  private Map<Integer, Player> playerMap;
  private Map<Integer, GameObject> gameObjects;
  private List<Entry<BiPredicate<Integer, GameObjectProperties>,
      BiFunction<Integer, GameObjectProperties, PhysicsHandler>>> conditionsList;

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
        Optional<Strikeable> optionalStrikeable = gameObjects.get(i)
            .getStrikeable();
        optionalStrikeable.ifPresent(playerStrikeableObjects::add);
      }
      playerMap.get(playerId).addStrikeables(playerStrikeableObjects);

      List<Integer> playerScoreableIds = parserPlayer.myScoreable();
      List<Scoreable> playerScoreableObjects = new ArrayList<>();
      for (int i : playerScoreableIds) {
        Optional<Scoreable> optionalStrikeable = gameObjects.get(i)
            .getScoreable();
        optionalStrikeable.ifPresent(playerScoreableObjects::add);
      }
      playerMap.get(playerId).addScoreables(playerScoreableObjects);
      if (!parserPlayer.myControllable().isEmpty()) {
        Optional<Controllable> optionalControllable = gameObjects.get(
            parserPlayer.myControllable().get(0)).getControllable();
        optionalControllable.ifPresent(controllable -> {
          playerMap.get(playerId).setControllable(controllable,
              parserPlayer.myControllable().get(1), parserPlayer.myControllable().get(2));
        });
      }
    }
  }

  /**
   * Retrieves the collidable container.
   *
   * @return The collidable container.
   */
  public Collection<GameObject> getGameObjects() {
    return gameObjects.values();
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
    gameObjects = new HashMap<>();
    gameData.getGameObjects().forEach(co -> {
      if (co.properties().contains("collidable")) {
        this.collidables.add(co.collidableId());
      }
      gameObjects.put(co.collidableId(), CollidableFactory.createCollidable(co));

    });
    gameData.getGameObjects().forEach(co -> {
      gameObjects.keySet().forEach(id -> addPairToPhysicsMap(co,
          id,
          conditionsList));
    });
  }

  private void addPairToPhysicsMap(GameObjectProperties co, int id,
      List<Entry<BiPredicate<Integer, GameObjectProperties>, BiFunction<Integer, GameObjectProperties, PhysicsHandler>>> conditionsList) {
    for (Entry<BiPredicate<Integer, GameObjectProperties>,
        BiFunction<Integer, GameObjectProperties, PhysicsHandler>> entry : conditionsList) {
      if (entry.getKey().test(id, co) && id != co.collidableId()) {
        physicsMap.put(new Pair(gameObjects.get(id), gameObjects.get(co.collidableId())),
            entry.getValue().apply(id, co));
        break;
      }
    }
  }

  private void createCollisionTypeMap() {
    conditionsList = List.of(
        Map.entry(
            (key, co) -> collidables.contains(key) && co.properties().contains("collidable"),
            (key, co) -> new MomentumHandler(gameObjects.get(key), gameObjects.get(co.collidableId()))
        ),
        Map.entry(
            (key, co) -> collidables.contains(key) || co.properties().contains("collidable"),
            (key, co) -> new FrictionHandler(gameObjects.get(key), gameObjects.get(co.collidableId()))
        )
    );
  }

  private void createPlayerContainer() {
    playerMap = new HashMap<>();
    gameData.getPlayers().forEach(p -> {
      playerMap.put(p.playerId(), new Player(p.playerId()));
      Player player = new Player(p.playerId());
      playerMap.put(p.playerId(), player);
    });
    this.playerContainer = new PlayerContainer(playerMap.values());
  }

  private void createRulesRecord() {
    Rules rules = gameData.getRules();
    Map<Pair, List<Command>> commandMap = createCommandMap();
    List<Command> advanceTurnCmds = createCommands(rules.advanceTurn());
    List<Command> advanceRoundCmds = createCommands(rules.advanceRound());
    Condition winCondition = createCondition(rules.winCondition());
    Condition roundPolicy = createCondition(rules.roundPolicy());
    TurnPolicy turnPolicy = TurnPolicyFactory.createTurnPolicy(rules.turnPolicy(),
        playerContainer);;
    StrikePolicy strikePolicy = StrikePolicyFactory.createStrikePolicy(rules.strikePolicy());
    PlayerRecordComparator comp = PlayerRankComparatorFactory.createRankComparator(rules.rankComparator());
    List<StaticChecker> checkers =  StaticCheckerFactory.createStaticChecker(rules.staticChecker());
    rulesRecord = new RulesRecord(commandMap,
        winCondition, roundPolicy, advanceTurnCmds, advanceRoundCmds, physicsMap, turnPolicy,
        staticHandler, strikePolicy, comp, checkers);
  }

  private Condition createCondition(Map<String, List<Integer>> conditionToParams) {
    if (conditionToParams.keySet().iterator().hasNext()) {
      String conditionName = conditionToParams.keySet().iterator().next();
      return ConditionFactory.createCondition(conditionName, conditionToParams.get(conditionName)
          , gameObjects);
    } else {
      throw new InvalidCommandException("");
    }
  }

  private List<Command> createCommands(Map<String, List<Integer>> commands) {
    return commands.keySet().stream().map(command -> CommandFactory.createCommand(command,
        commands.get(command), gameObjects)).collect(Collectors.toList());
  }

  private Map<Pair, List<Command>> createCommandMap() {
    Map<Pair, List<Command>> commandMap = new HashMap<>();
    gameData.getRules().collisions().forEach(rule -> {
      List<Command> commands = new ArrayList<>();
      rule.command().forEach((cmdName, params) -> {
        commands.add(CommandFactory.createCommand(cmdName, params, gameObjects));
      });
      commandMap.put(new Pair(gameObjects.get(rule.firstId()), gameObjects.get(rule.secondId())),
          commands);
    });
    return commandMap;
  }

}
