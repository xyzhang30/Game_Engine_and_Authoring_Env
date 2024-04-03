package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.gameengine.GameEngine;
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
import oogasalad.model.gameparser.GameLoaderModel;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

public class GameEngineTest {

  public static class GameLoaderMock extends GameLoaderModel {

    private PlayerContainer playerContainer;
    private CollidableContainer collidableContainer;
    private oogasalad.model.gameengine.RulesRecord rules;
    private GameEngine engine;
    private TurnPolicy turnPolicy;

    public GameLoaderMock(String id) {
      super(id);
      createCollidableContainer();
      createPlayerContainer();
      createRulesRecord();
      createTurnPolicy();
    }

    @Override
    public void createPlayerContainer() {
      Map<Integer, Player> mockPlayers = new HashMap<>();
      mockPlayers.put(1, new Player(1, collidableContainer.getCollidable(1)));
      mockPlayers.put(2, new Player(2, collidableContainer.getCollidable(4)));

      this.playerContainer = new PlayerContainer(mockPlayers);
    }


    protected void createCollidableContainer() {
      Map<Integer, Collidable> mockCollidables = new HashMap<>();
      mockCollidables.put(1, new Moveable(1, 50, 0, 0, true));
      mockCollidables.put(2, new Surface(2, Double.MAX_VALUE, 0, 0, true, .2));
      mockCollidables.put(3, new Moveable(3, Double.MAX_VALUE, 100, 100, true));
      mockCollidables.put(4, new Moveable(4, Double.MAX_VALUE, 70, 0, true));
      mockCollidables.put(5, new Moveable(5, 50, 40, 40, true));
      mockCollidables.put(6, new Moveable(6, 50, 60, 40, true));

      this.collidableContainer = new CollidableContainer(mockCollidables);
    }


    protected void createTurnPolicy() {
      turnPolicy = new StandardTurnPolicy(playerContainer);
    }
    protected void createRulesRecord() {
      Map<Pair, List<Command>> myMap = new HashMap<>();
      myMap.put(new Pair(1, 4), List.of(new AdjustPointsCommand(List.of(1.0, 10.0)),
          new AdvanceTurnCommand(List.of())));
      this.rules = new oogasalad.model.gameengine.RulesRecord(1, Integer.MAX_VALUE, myMap,
          new NRoundsCompletedCommand(List.of(3.0)));
    }

    @Override
    public PlayerContainer getPlayerContainer() {
      return playerContainer;
    }

    @Override
    public CollidableContainer getCollidableContainer() {
      return collidableContainer;
    }

    @Override
    public RulesRecord getRulesRecord() {
      return rules;
    }

    @Override
    public TurnPolicy getTurnPolicy() {
      return turnPolicy;
    }
  }

  private GameEngine gameEngine;
  private GameLoaderModel loaderMock;

  @BeforeEach
  public void setUp() {
    gameEngine = new GameEngine("data/singlePlayerMiniGolf.json");
    gameEngine.start(new GameLoaderMock("data/singlePlayerMiniGolf.json"));
  }


  @Test
  public void testStartAndResetGame() {
    // Ensure the game starts without errors


    // Assert that the initial round and turn are as expected
    assertEquals(1, gameEngine.getRound());
    assertEquals(1, gameEngine.getTurn());

    // Reset the game and verify that it's back to initial state
    gameEngine.reset();
    assertEquals(1, gameEngine.getRound());
    assertEquals(1, gameEngine.getTurn());
  }


  @Test
  public void testOnApplyVelocity() {
    // Ensure the game starts without errors

    gameEngine.applyInitialVelocity(10, 0, 1);
    double vel =
        gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord().velocityX();
    // Assert that the initial round and turn are as expected
    assertEquals(10, vel);
  }

  @Test
  public void testSingularUpdate() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(10, 0, 1);

    gameEngine.handleCollisions(List.of(new Pair(1, 2)), 1);
    System.out.println(gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord());
    assertEquals(8, gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord().x());
    assertEquals(8,
        gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord().velocityX());

  }

  @Test
  public void testMultipleUpdate() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(10, 0, 1);

    gameEngine.handleCollisions(List.of(new Pair(1, 2)), 1);
    gameEngine.handleCollisions(List.of(new Pair(1, 2)), 1);
    System.out.println(gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord());
    assertEquals(14,
        gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord().x());
    assertEquals(6,
        gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord().velocityX());

  }


  @Test
  public void testCollide() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(12, 0, 5);
    gameEngine.applyInitialVelocity(12, Math.PI, 6);

    gameEngine.handleCollisions(List.of(new Pair(5, 2), new Pair(6, 2)), 1);

    assertEquals(50,
        gameEngine.getCollidableContainer().getCollidable(5).getCollidableRecord().x());
    assertEquals(10,
        gameEngine.getCollidableContainer().getCollidable(5).getCollidableRecord().velocityX());
    assertEquals(50,
        gameEngine.getCollidableContainer().getCollidable(6).getCollidableRecord().x());
    assertEquals(-10,
        gameEngine.getCollidableContainer().getCollidable(6).getCollidableRecord().velocityX());
    gameEngine.handleCollisions(List.of(new Pair(5, 6), new Pair(5, 2), new Pair(6, 2)), 1);
    assertEquals(42,
        gameEngine.getCollidableContainer().getCollidable(5).getCollidableRecord().x());
    assertEquals(-8,
        gameEngine.getCollidableContainer().getCollidable(5).getCollidableRecord().velocityX());
    assertEquals(58,
        gameEngine.getCollidableContainer().getCollidable(6).getCollidableRecord().x());
    assertEquals(8,
        gameEngine.getCollidableContainer().getCollidable(6).getCollidableRecord().velocityX());
  }

  @Test
  public void testStop() {
    // Ensure the game starts without errors

    HashMap<Integer, Integer> map = new HashMap<>(Map.of(0, 5, 1, 3, 2, 1, 3, 0, 4, 0));
    gameEngine.applyInitialVelocity(7, 0, 5);
    System.out.println(gameEngine.getCollidableContainer().getCollidable(5).getCollidableRecord());
    for (int i = 0; i < 5; i++) {
      gameEngine.handleCollisions(List.of(new Pair(5, 2)), 1);

      System.out.println(
          gameEngine.getCollidableContainer().getCollidable(5).getCollidableRecord());
      System.out.println(
          gameEngine.getCollidableContainer().getCollidable(5).getCollidableRecord().velocityX());

      assertEquals((double) map.get(i),
          gameEngine.getCollidableContainer().getCollidable(5).getCollidableRecord().velocityX());
    }

  }

  @Test
  public void testAdvanceTurnAndAdjustPoints() {
    // Ensure the game starts without errors
    gameEngine.handleCollisions(List.of(new Pair(1, 4)), 1);
    assertEquals(2, gameEngine.getTurn());
    System.out.println(gameEngine.getPlayerContainer().getPlayerRecords());
    assertEquals(10, gameEngine.getPlayerContainer().getPlayer(1).getVariable("score"));
  }
}
