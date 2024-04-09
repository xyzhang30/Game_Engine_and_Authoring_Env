package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.gameengine.GameEngine;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

public class GameEngineTest {


  private GameEngine gameEngine;


  @BeforeEach
  public void setUp() {
    gameEngine = new GameEngine("testPhysics");

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

    gameEngine.update(1.0/40);
    gameEngine.handleCollisions(List.of(new Pair(1, 2)), 1.0/40);
    assertEquals(.25,
        gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord().x());
    assertEquals(8,
        gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord().velocityX());

  }

  @Test
  public void testMultipleUpdate() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(10, 0, 1);

    gameEngine.update(1.0/40);
    gameEngine.handleCollisions(List.of(new Pair(1, 2)), 1.0/40);
    gameEngine.update(1.0/40);
    gameEngine.handleCollisions(List.of(new Pair(1, 2)), 1.0/40);
    assertEquals(.45,
        gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord().x());
    assertEquals(6,
        gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord().velocityX());
  }


  @Test
  public void testCollide() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(10, 0, 5);
    gameEngine.applyInitialVelocity(10, Math.PI, 6);

    gameEngine.update(1.0);
    gameEngine.handleCollisions(List.of(new Pair(5, 2), new Pair(6, 2), new Pair(5,6)), 1.0/40);

    assertEquals(50,
        gameEngine.getCollidableContainer().getCollidable(5).getCollidableRecord().x());
    assertEquals(-8,
        gameEngine.getCollidableContainer().getCollidable(5).getCollidableRecord().velocityX());
    assertEquals(50,
        gameEngine.getCollidableContainer().getCollidable(6).getCollidableRecord().x());
    assertEquals(8,
        gameEngine.getCollidableContainer().getCollidable(6).getCollidableRecord().velocityX());
    gameEngine.update(1.0/40);
    gameEngine.handleCollisions(List.of(new Pair(5, 2), new Pair(6, 2)), 1.0/40);
    assertEquals(49.8,
        gameEngine.getCollidableContainer().getCollidable(5).getCollidableRecord().x());
    assertEquals(-6,
        gameEngine.getCollidableContainer().getCollidable(5).getCollidableRecord().velocityX());
    assertEquals(50.2,
        gameEngine.getCollidableContainer().getCollidable(6).getCollidableRecord().x());
    assertEquals(6,
        gameEngine.getCollidableContainer().getCollidable(6).getCollidableRecord().velocityX());
  }

  @Test
  public void testStop() {
    // Ensure the game starts without errors


    HashMap<Integer, Integer> map = new HashMap<>(Map.of(0, 5, 1, 3, 2, 1, 3, 0, 4, 0));
    gameEngine.applyInitialVelocity(5, 0, 5);
    System.out.println(gameEngine.getCollidableContainer().getCollidable(5));
    for (int i = 0; i < 5; i++) {
      assertEquals((double) map.get(i),
          gameEngine.getCollidableContainer().getCollidable(5).getCollidableRecord().velocityX());

      gameEngine.update(1.0/40);
      System.out.println(gameEngine.getCollidableContainer().getCollidable(5));
      gameEngine.handleCollisions(List.of(new Pair(5, 2)), 1.0/40);
      System.out.println(gameEngine.getCollidableContainer().getCollidable(5));
    }

  }

  @Test
  public void testAdvanceTurnAndAdjustPoints() {
    // Ensure the game starts without errors
    gameEngine.handleCollisions(List.of(new Pair(1, 4)), 1.0/40);
    assertEquals(2, gameEngine.getTurn());
    assertEquals(10, gameEngine.getPlayerContainer().getPlayer(1).getVariable("score"));
  }

  @Test
  public void testMoveWithoutCollision() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(10.0,0,1);
    gameEngine.update(1);
    gameEngine.handleCollisions(List.of(), 1);
    assertEquals(10,
        gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord().velocityX());
    assertEquals(10,
        gameEngine.getCollidableContainer().getCollidable(1).getCollidableRecord().x());
  }


  @Test
  public void testUndoCommand() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(2,0,8);
    gameEngine.update(1.0/40);
    gameEngine.handleCollisions(List.of(new Pair(8,2)), 1.0/40); //move
    gameEngine.update(1.0/40);
    gameEngine.applyInitialVelocity(2,Math.PI/2,8);
    gameEngine.update(1.0/40);
    gameEngine.handleCollisions(List.of(new Pair(8,2)), 1.0/40); //move
    gameEngine.update(1.0/40);
    gameEngine.handleCollisions(List.of(new Pair(7,8),new Pair(8,2)), 1.0/40);
    assertEquals(0,
        gameEngine.getCollidableContainer().getCollidable(8).getCollidableRecord().velocityX());

    assertEquals(0,
        gameEngine.getCollidableContainer().getCollidable(8).getCollidableRecord().velocityY());
    assertEquals(48.0,
        gameEngine.getCollidableContainer().getCollidable(8).getCollidableRecord().y());
    assertEquals(50,
        gameEngine.getCollidableContainer().getCollidable(8).getCollidableRecord().x());

    gameEngine.handleCollisions(List.of(new Pair(7,8),new Pair(8,2)), 1.0/40);

    assertEquals(0,
        gameEngine.getCollidableContainer().getCollidable(8).getCollidableRecord().velocityX());

    assertEquals(0,
        gameEngine.getCollidableContainer().getCollidable(8).getCollidableRecord().velocityY());
    assertEquals(48.0,
        gameEngine.getCollidableContainer().getCollidable(8).getCollidableRecord().y());
    assertEquals(48.0,
        gameEngine.getCollidableContainer().getCollidable(8).getCollidableRecord().x());

  }

}
