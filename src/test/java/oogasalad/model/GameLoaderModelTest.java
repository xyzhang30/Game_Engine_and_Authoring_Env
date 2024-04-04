package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.api.exception.InvalidFileException;
import oogasalad.model.gameengine.Player;
import oogasalad.model.gameengine.PlayerContainer;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.StandardTurnPolicy;
import oogasalad.model.gameengine.TurnPolicy;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameengine.collidable.Moveable;
import oogasalad.model.gameengine.collidable.Surface;
import oogasalad.model.gameengine.command.AdjustPointsCommand;
import oogasalad.model.gameengine.command.AdvanceTurnCommand;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.command.NRoundsCompletedCommand;
import oogasalad.model.gameparser.GameLoader;
import oogasalad.model.gameparser.GameLoaderModel;
import oogasalad.model.gameparser.data.CollidableObject;
import oogasalad.model.gameparser.data.CollisionRule;
import oogasalad.model.gameparser.data.Dimension;
import oogasalad.model.gameparser.data.GameData;
import oogasalad.model.gameparser.data.GlobalVariables;
import oogasalad.model.gameparser.data.ParserPlayer;
import oogasalad.model.gameparser.data.PlayerVariables;
import oogasalad.model.gameparser.data.Position;
import oogasalad.model.gameparser.data.Rules;
import oogasalad.model.gameparser.data.Variables;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

public class GameLoaderModelTest {
  GameLoaderModel testGameLoaderModel;
  CollidableContainer mockCollidableContainer;
  PlayerContainer mockPlayerContainer;

  @BeforeEach
  public void setup() {
    String gameTitle = "singlePlayerMiniGolf";
    this.testGameLoaderModel = new GameLoaderModel(gameTitle);

    Collidable c1 = new Surface(1, 1000000, 0,0, true, 0.5);
    Collidable c2 = new Moveable(2, 1, 250, 450, true);
    Collidable c3 = new Surface(3, 0, 0,0, true, 0);
    Collidable c4 = new Moveable(4, 200, 0, 0, true);
    Collidable c5 = new Moveable(5, 200, 0, 0, true);
    Collidable c6 = new Moveable(6, 200, 490, 0, true);
    Collidable c7 = new Moveable(7, 200, 0, 490, true);

    Map<Integer, Collidable> collidables = Map.of(1, c1, 2, c2, 3, c3, 4, c4, 5, c5, 6, c6, 7, c7);

    this.mockCollidableContainer = new CollidableContainer(collidables);

    Player p1 = new Player(1, mockCollidableContainer.getCollidable(2));

    Map<Integer, Player> players = Map.of(1, p1);

    this.mockPlayerContainer = new PlayerContainer(players);
  }

//  @Test(expected = IndexOutOfBoundsException.class)
//  public void testInvalidJSONFile() {
//
//  }
  @Test
  public void testParseCollidables() {
    assertEquals(this.testGameLoaderModel.getCollidableContainer(), mockCollidableContainer);
  }

  @Test
  public void testParsePlayers() {
    assertEquals(this.testGameLoaderModel.getPlayerContainer(), mockPlayerContainer);
  }

  @Test
  public void testParseTurnPolicy() {
    TurnPolicy mockTurnPolicy = new StandardTurnPolicy(mockPlayerContainer);
    assertEquals(this.testGameLoaderModel.getTurnPolicy(), mockTurnPolicy);
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

    RulesRecord mockRulesRecord = new RulesRecord(1, 1, collisionHandlers, winCondition, advanceCs);

    assertEquals(this.testGameLoaderModel.getRulesRecord(), mockRulesRecord);
  }



}
