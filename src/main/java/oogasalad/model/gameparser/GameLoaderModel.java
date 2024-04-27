package oogasalad.model.gameparser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
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
import oogasalad.model.gameengine.gameobject.collision.FrictionHandler;
import oogasalad.model.gameengine.gameobject.collision.MomentumHandler;
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

  public static final String BASE_PATH = "oogasalad.model.gameengine.";
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
    collidables = new ArrayList<>();
    physicsMap = new HashMap<>();
    playerMap = new HashMap<>();
    gameData.getPlayers().forEach(p -> playerMap.put(p.playerId(), new Player(p.playerId())));
    playerContainer = new PlayerContainer(playerMap.values());
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

  /**
   * Retrieves the collidable container.
   *
   * @return The collidable container.
   */
  public Collection<GameObject> getGameObjects() {
    createGameObjectContainer();
    addPlayerObjects(ParserPlayer::myStrikeable,
        gameId -> gameObjects.get(gameId).getStrikeable(),
        (playerId, strikeables) -> playerMap.get(playerId).addStrikeables(strikeables));
    addPlayerObjects(ParserPlayer::myScoreable,
        gameId -> gameObjects.get(gameId).getScoreable(),
        (playerId, scoreables) -> playerMap.get(playerId).addScoreables(scoreables));
    addPlayerControllables();
    return gameObjects.values();
  }

  /**
   * Retrieves the rules record.
   *
   * @return The rules record.
   */
  public RulesRecord getRulesRecord() {
    createRulesRecord();
    return rulesRecord;
  }

  private <T> void addPlayerObjects(Function<? super ParserPlayer, ? extends List<Integer>> scoreableIdExtractor,
      Function<? super Integer, ? extends Optional<? extends T>> scoreableObjectExtractor,
      BiConsumer<Integer, List<T>> playerMethod) {
    for (ParserPlayer parserPlayer : gameData.getPlayers()) {
      int playerId = parserPlayer.playerId();
      List<Integer> playerScoreableIds = scoreableIdExtractor.apply(parserPlayer);
      List<T> playerScoreableObjects = new ArrayList<>();
      for (int i : playerScoreableIds) {
        Optional<? extends T> optionalScoreable = scoreableObjectExtractor.apply(i);
        optionalScoreable.ifPresent(playerScoreableObjects::add);
      }
      playerMethod.accept(playerId, playerScoreableObjects);
    }
  }


  private void addPlayerControllables() {
    gameData.getPlayers().stream()
        .filter(parserPlayer -> !parserPlayer.myControllable().isEmpty())
        .forEach(parserPlayer -> {
          parserPlayer.myControllable().stream()
              .map(gameObjects::get)
              .map(GameObject::getControllable)
              .filter(Optional::isPresent)
              .map(Optional::get)
              .findFirst()
              .ifPresent(controllable -> playerMap.get(parserPlayer.playerId()).setControllable(controllable,
                  parserPlayer.myControllable().get(1), parserPlayer.myControllable().get(2)));
        });
  }

  private void createGameObjectContainer() {
    gameObjects = new HashMap<>();
    populateGameObjects();
    gameData.getGameObjectProperties().forEach(co -> {
      gameObjects.keySet().forEach(id -> addPairToPhysicsMap(co, id, conditionsList));
    });
  }

  private void populateGameObjects() {
    gameData.getGameObjectProperties().forEach(co -> {
      if (co.properties().contains("collidable")) {
        this.collidables.add(co.collidableId());
      }
      gameObjects.put(co.collidableId(), GameObjectFactory.createCollidable(co));
    });
  }

  private void addPairToPhysicsMap(GameObjectProperties co, int id,
      List<Entry<BiPredicate<Integer, GameObjectProperties>, BiFunction<Integer, GameObjectProperties, PhysicsHandler>>> conditionsList) {
    conditionsList.stream()
        .filter(entry -> entry.getKey().test(id, co) && id != co.collidableId())
        .findFirst()
        .ifPresent(entry -> physicsMap.put(new Pair(gameObjects.get(id), gameObjects.get(co.collidableId())),
            entry.getValue().apply(id, co)));
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


  private void createRulesRecord() {
    Rules rules = gameData.getRules();
    Map<Pair, List<Command>> commandMap = createCommandMap();
    List<Command> advanceTurnCmds = createCommands(rules.advanceTurn());
    List<Command> advanceRoundCmds = createCommands(rules.advanceRound());
    Condition winCondition = createCondition(rules.winCondition());
    Condition roundCondition = createCondition(rules.roundCondition());
    TurnPolicy turnPolicy = GenericFactory.createTurnPolicy(rules.turnPolicy(), playerContainer);
    StrikePolicy strikePolicy = GenericFactory.createStrikePolicy(rules.strikePolicy());
    PlayerRecordComparator comp = GenericFactory.createRankComparator(rules.rankComparator());
    List<StaticChecker> checkers =  StaticCheckerFactory.createStaticChecker(rules.staticChecker());
    rulesRecord = new RulesRecord(commandMap,
        winCondition, roundCondition, advanceTurnCmds, advanceRoundCmds, physicsMap, turnPolicy,
        staticHandler, strikePolicy, comp, checkers);
  }

  private Condition createCondition(Map<String, List<Integer>> conditionToParams) {
    return conditionToParams.keySet().stream()
        .findFirst()
        .map(conditionName -> ExecutableFactory.createCondition(conditionName,
            conditionToParams.get(conditionName), gameObjects))
        .orElseThrow(() -> new InvalidCommandException(""));
  }

  private List<Command> createCommands(Map<String, List<Integer>> commands) {
    return commands.keySet().stream().map(command -> ExecutableFactory.createCommand(command,
        commands.get(command), gameObjects)).collect(Collectors.toList());
  }

  private Map<Pair, List<Command>> createCommandMap() {
    return gameData.getRules().collisions().stream()
        .collect(Collectors.toMap(
            rule -> new Pair(gameObjects.get(rule.firstId()), gameObjects.get(rule.secondId())),
            rule -> rule.command().entrySet().stream()
                .map(entry -> ExecutableFactory.createCommand(entry.getKey(), entry.getValue(), gameObjects))
                .collect(Collectors.toList())));
  }

}
