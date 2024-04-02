package oogasalad.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import oogasalad.Pair;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameparser.GameLoaderModel;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

public class GameEngineTest {

  private GameEngine gameEngine;
  private GameLoaderModel loaderMock;

  @BeforeEach
  public void setUp() {
    gameEngine = new GameEngine(new GameLoaderMock(1));
  }


  @Test
  public void testStartAndResetGame() {
    // Ensure the game starts without errors
    gameEngine.start();

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
    gameEngine.start();
    gameEngine.applyInitialVelocity(10, 0, 1);
    double vel =
        gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord().velocityX();
    // Assert that the initial round and turn are as expected
    assertEquals(10, vel);
  }

  @Test
  public void testUpdate() {
    // Ensure the game starts without errors
    gameEngine.start();
    gameEngine.applyInitialVelocity(10, 0, 1);
    // Assert that the initial round and turn are as expected
    gameEngine.update(1);
    gameEngine.handleCollisions(List.of(new Pair(1,2)), 1);
    assertEquals(10,gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord().x());
    assertEquals(   5,
        gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord().velocityX());

  }
}
