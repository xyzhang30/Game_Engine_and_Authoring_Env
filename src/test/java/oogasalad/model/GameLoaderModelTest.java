package oogasalad.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.api.exception.InvalidFileException;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.player.PlayerContainer;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.collidable.PhysicsHandler;
import oogasalad.model.gameengine.collidable.collision.FrictionHandler;
import oogasalad.model.gameengine.collidable.collision.MomentumHandler;
import oogasalad.model.gameengine.command.AdvanceRoundCommand;
import oogasalad.model.gameengine.condition.AllPlayersCompletedRoundCondition;
import oogasalad.model.gameengine.condition.Condition;
import oogasalad.model.gameengine.condition.NRoundsCompletedCondition;
import oogasalad.model.gameengine.statichandlers.GenericStaticStateHandler;
import oogasalad.model.gameengine.statichandlers.StaticStateHandlerLinkedListBuilder;
import oogasalad.model.gameengine.turn.StandardTurnPolicy;
import oogasalad.model.gameengine.turn.TurnPolicy;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameengine.command.AdjustPointsCommand;
import oogasalad.model.gameengine.command.AdvanceTurnCommand;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameparser.GameLoaderModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameLoaderModelTest {
  GameLoaderModel testGameLoaderModel;
  CollidableContainer mockCollidableContainer;
  PlayerContainer mockPlayerContainer;
  TurnPolicy mockTurnPolicy;
  @BeforeEach
  public void setup() {
    String gameTitle = "testSinglePlayerMiniGolf";
    this.testGameLoaderModel = new GameLoaderModel(gameTitle);
    testGameLoaderModel.makeLevel(1);
    Collidable c1 = new Collidable(1, Double.POSITIVE_INFINITY, 0,0, true, 3.03873, 2.03873, 500, 500, "rectangle");
    Collidable c2 = new Collidable(2, 1, 250, 450, true, 0, 0, 2, 2, "circle");
    Collidable c3 = new Collidable(3, 0, 250,50, true, 0,0, 5, 5, "circle");
    Collidable c4 = new Collidable(4, 200, 0, 0, true, 0,0, 500, 10, "rectangle");
    Collidable c5 = new Collidable(5, 200, 0, 0, true, 0,0, 10, 500, "rectangle");
    Collidable c6 = new Collidable(6, 200, 490, 0, true, 0,0, 10, 500, "rectangle");
    Collidable c7 = new Collidable(7, 200, 0, 490, true, 0,0, 500, 10, "rectangle");

    Map<Integer, Collidable> collidables = Map.of(1, c1, 2, c2, 3, c3, 4, c4, 5, c5, 6, c6, 7, c7);

    this.mockCollidableContainer = new CollidableContainer(collidables);

    Player p1 = new Player(1);

    Map<Integer, Player> players = Map.of(1, p1);

    this.mockPlayerContainer = new PlayerContainer(players);
    mockTurnPolicy = new StandardTurnPolicy(mockPlayerContainer);
  }

  @Test
  public void testInvalidJSONFile() {

    InvalidFileException exception = assertThrows(InvalidFileException.class, () -> {
      String invalidFileName = "invalidConfigurationFormat";
      testGameLoaderModel = new GameLoaderModel(invalidFileName);
    });

    String expectedMessage = "Error parsing JSON game configuration file:";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));

  }

  @Test
  public void testParseCollidables() {
    assertThat(testGameLoaderModel.getCollidableContainer()).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(mockCollidableContainer);
  }

  @Test
  public void testParsePlayers() {
    assertThat(testGameLoaderModel.getPlayerContainer()).usingRecursiveComparison().isEqualTo(mockPlayerContainer);
  }

  @Test
  public void testParseTurnPolicy() {
    TurnPolicy mockTurnPolicy = new StandardTurnPolicy(mockPlayerContainer);
    assertThat(testGameLoaderModel.getRulesRecord().turnPolicy().getClass()).isEqualTo(mockTurnPolicy.getClass());

  }

  @Test
  public void testParseRules() {
    Command c1 = new AdjustPointsCommand(List.of(1.0,1.0));
    Command c2 = new AdvanceTurnCommand(List.of());
    Map<Pair, List<Command>> collisionHandlers = Map.of(new Pair(2, 3), List.of(c1, c2));
    Condition winCondition = new NRoundsCompletedCondition(List.of(2.0));

    Condition roundPolicy = new AllPlayersCompletedRoundCondition(List.of());

    Command advanceC1 = new AdvanceTurnCommand(List.of());
    Command advanceC2 = new AdjustPointsCommand(List.of(1.0, 1.0));
    List<Command> advanceTurn = List.of(advanceC1, advanceC2);

    Command advanceC3 = new AdvanceRoundCommand(List.of());
    List<Command> advanceRound = List.of(advanceC3);

    GenericStaticStateHandler mockStaticStateHandler = StaticStateHandlerLinkedListBuilder.buildLinkedList(List.of(
        "GameOverStaticStateHandler",
        "RoundOverStaticStateHandler", "TurnOverStaticStateHandler"));


    Map<Pair, PhysicsHandler> physicsMap = new HashMap<>();
    physicsMap.put(new Pair(1, 2), new FrictionHandler(1, 2));
    physicsMap.put(new Pair(2, 3), new FrictionHandler(2, 3));
    physicsMap.put(new Pair(3, 4), new FrictionHandler(3, 4));
    physicsMap.put(new Pair(4, 5), new MomentumHandler(4, 5));
    physicsMap.put(new Pair(5, 6), new MomentumHandler(5, 6));
    physicsMap.put(new Pair(6, 7), new MomentumHandler(6, 7));
    physicsMap.put(new Pair(2, 4), new MomentumHandler(2, 4));
    physicsMap.put(new Pair(3, 5), new FrictionHandler(3, 5));
    physicsMap.put(new Pair(4, 6), new MomentumHandler(4, 6));
    physicsMap.put(new Pair(5, 7), new MomentumHandler(5, 7));
    physicsMap.put(new Pair(1, 4), new FrictionHandler(1, 4));
    physicsMap.put(new Pair(2, 5), new MomentumHandler(2, 5));
    physicsMap.put(new Pair(3, 6), new FrictionHandler(3, 6));
    physicsMap.put(new Pair(4, 7), new MomentumHandler(4, 7));
    physicsMap.put(new Pair(1, 5), new FrictionHandler(1, 5));
    physicsMap.put(new Pair(2, 6), new MomentumHandler(2, 6));
    physicsMap.put(new Pair(3, 7), new FrictionHandler(3, 7));
    physicsMap.put(new Pair(1, 6), new FrictionHandler(1, 6));
    physicsMap.put(new Pair(2, 7), new MomentumHandler(2, 7));
    physicsMap.put(new Pair(1, 7), new FrictionHandler(1, 7));

    RulesRecord mockRulesRecord = new RulesRecord(collisionHandlers, winCondition, roundPolicy,
        advanceTurn, advanceRound, physicsMap, mockTurnPolicy, mockStaticStateHandler);

    testGameLoaderModel.getCollidableContainer();
    assertThat(testGameLoaderModel.getRulesRecord()).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(mockRulesRecord);
  }



}
