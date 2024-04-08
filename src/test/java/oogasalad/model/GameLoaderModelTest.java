package oogasalad.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.api.exception.InvalidFileException;
import oogasalad.model.gameengine.Player;
import oogasalad.model.gameengine.PlayerContainer;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.turn.StandardTurnPolicy;
import oogasalad.model.gameengine.turn.TurnPolicy;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameengine.collidable.Moveable;
import oogasalad.model.gameengine.collidable.Surface;
import oogasalad.model.gameengine.command.AdjustPointsCommand;
import oogasalad.model.gameengine.command.AdvanceTurnCommand;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.command.NRoundsCompletedCommand;
import oogasalad.model.gameparser.GameLoaderModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameLoaderModelTest {
  GameLoaderModel testGameLoaderModel;
  CollidableContainer mockCollidableContainer;
  PlayerContainer mockPlayerContainer;

  @BeforeEach
  public void setup() {
    String gameTitle = "testSinglePlayerMiniGolf";
    this.testGameLoaderModel = new GameLoaderModel(gameTitle);

    Collidable c1 = new Surface(1, Double.POSITIVE_INFINITY, 0,0, true, 0.5, 500, 500);
    Collidable c2 = new Moveable(2, 1, 250, 450, true, 2, 2);
    Collidable c3 = new Surface(3, 0, 250,50, true, 0, 5, 5);
    Collidable c4 = new Moveable(4, 200, 0, 0, true, 500, 10);
    Collidable c5 = new Moveable(5, 200, 0, 0, true, 10, 500);
    Collidable c6 = new Moveable(6, 200, 490, 0, true, 10, 500);
    Collidable c7 = new Moveable(7, 200, 0, 490, true, 500, 10);

    Map<Integer, Collidable> collidables = Map.of(1, c1, 2, c2, 3, c3, 4, c4, 5, c5, 6, c6, 7, c7);

    this.mockCollidableContainer = new CollidableContainer(collidables);

    Player p1 = new Player(1, c2);

    Map<Integer, Player> players = Map.of(1, p1);

    this.mockPlayerContainer = new PlayerContainer(players);
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
    assertThat(testGameLoaderModel.getTurnPolicy()).usingRecursiveComparison().isEqualTo(mockTurnPolicy);

  }

  @Test
  public void testParseRules() {
    Command c1 = new AdjustPointsCommand(List.of(1.0,1.0));
    Command c2 = new AdvanceTurnCommand(List.of());
    Map<Pair, java.util.List<Command>> collisionHandlers = Map.of(new Pair(2, 3), List.of(c1, c2));
    Command winCondition = new NRoundsCompletedCommand(List.of(2.0));

    Command advanceC1 = new AdvanceTurnCommand(List.of());
    Command advanceC2 = new AdjustPointsCommand(List.of(1.0, 1.0));
    List<Command> advanceCs = List.of(advanceC1, advanceC2);

    RulesRecord mockRulesRecord = new RulesRecord(1, 1, collisionHandlers, winCondition, advanceCs,
        physicsMap);

    assertThat(testGameLoaderModel.getRulesRecord()).usingRecursiveComparison().isEqualTo(mockRulesRecord);
  }



}
