package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.model.api.CollidableRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

public class GameEngineTest {


  private GameEngine gameEngine;
  private CollidableRecord c1;
  private CollidableRecord c2;
  private CollidableRecord c3;
  private CollidableContainer container;

  private static final double DELTA = .0001;


  @BeforeEach
  public void setUp() {
    gameEngine = new GameEngine("testPhysics");
    container = gameEngine.getCollidableContainer();
  }

  private boolean isStatic(GameRecord r) {
    for(CollidableRecord cr : r.collidables()) {
      if(cr.velocityY()!=0 || cr.velocityX()!=0) {
        return false;
      }
    }
    return true;
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
    // Assert that the initial round and turn are as expected
    assertEquals(10, container.getCollidableRecord(1).velocityX());
  }

  @Test
  public void testSingularUpdate() {
    // Ensure the game starts without errors

    gameEngine.applyInitialVelocity(10, 0, 1);
    // Assert that the initial round and turn are as expected
    assertEquals(10, container.getCollidableRecord(1).velocityX());
    System.out.println(container.getCollidableRecord(1));
    gameEngine.update(1.0/4.0);
    System.out.println(container.getCollidableRecord(1));
    assertEquals(2.5, container.getCollidableRecord(1).x());
    assertEquals(5, container.getCollidableRecord(1).velocityX());
  }


  @Test
  public void testMultipleUpdate() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(15, 0, 1);
    assertEquals(15, container.getCollidableRecord(1).velocityX());
    gameEngine.update(1.0/4);
    assertEquals(15/4.0, container.getCollidableRecord(1).x());
    assertEquals(10, container.getCollidableRecord(1).velocityX());
    gameEngine.update(1.0/4);
    assertEquals(25/4.0, container.getCollidableRecord(1).x());
    assertEquals(5, container.getCollidableRecord(1).velocityX());
  }


  @Test
  public void testStop() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(15, Math.PI/2, 1);
    System.out.println(gameEngine.getCollidableContainer().getCollidableRecord(1));
    GameRecord r = gameEngine.update(1.0/4);
    System.out.println(r.collidables().get(0));
    while(!isStatic(r)) {
      r = gameEngine.update(1.0/4);
      System.out.println(r.collidables().get(0));

    }
    assertEquals(7.5, container.getCollidableRecord(1).y());
    assertEquals(0, container.getCollidableRecord(1).velocityY());
  }


  @Test
  public void testMoveAtAngle() {
    gameEngine.applyInitialVelocity(20, Math.PI/4, 1);
    gameEngine.update(.5);
    System.out.println(container.getCollidableRecord(1));
    assertEquals(10/Math.sqrt(2), container.getCollidableRecord(1).x(), DELTA);
    assertEquals(10/Math.sqrt(2), container.getCollidableRecord(1).y(), DELTA);
    assertEquals(10/Math.sqrt(2), container.getCollidableRecord(1).velocityX(), DELTA);
    assertEquals(10/Math.sqrt(2), container.getCollidableRecord(1).velocityX(), DELTA);
  }

  @Test
  public void testTwoMovingObjectsCollide() {
    gameEngine.applyInitialVelocity(15, -Math.PI, 1);
    gameEngine.applyInitialVelocity(15, 0, 10);
    gameEngine.update(.25);
    assertEquals(-10, container.getCollidableRecord(1).velocityX(), DELTA);
    assertEquals(10, container.getCollidableRecord(10).velocityX(), DELTA);
    gameEngine.update(.25);

    System.out.println(container.getCollidableRecord(1));

    System.out.println(container.getCollidableRecord(10));
//https://www.sciencecalculators.org/mechanics/collisions/
    assertEquals(1.666666666666, container.getCollidableRecord(1).velocityX(), DELTA);
    assertEquals(-8.3333333, container.getCollidableRecord(10).velocityX(), DELTA);
  }


  @Test
  public void testAdjustPointsCommand() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(15, -Math.PI, 1);
    gameEngine.applyInitialVelocity(15, 0, 10);
    GameRecord r = gameEngine.update(.5);
    assertEquals(2.0, r.players().get(0).score(), DELTA);
  }

  @Test
  public void testUndoCommand() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(100, 0, 1);
    gameEngine.update(1);
    assertEquals(0.0,container.getCollidableRecord(1).velocityX(), DELTA);
    assertEquals(0.0,container.getCollidableRecord(1).velocityY(), DELTA);
    assertEquals(0.0,container.getCollidableRecord(1).x(), DELTA);
    assertEquals(0.0,container.getCollidableRecord(1).y(), DELTA);
  }


  @Test
  public void testAdvanceTurnAndAdjustPoints() {
    gameEngine.applyInitialVelocity(15, 0, 1);
    assertEquals(1.0,gameEngine.getTurn(), DELTA);
    gameEngine.update(1);
    assertEquals(2.0,gameEngine.getTurn(), DELTA);
    gameEngine.update(1);
    assertEquals(1.0,gameEngine.getTurn(), DELTA);
    GameRecord r = gameEngine.update(1);
    //note the ball belongs to player 1 so they get all the points
    assertEquals(3.0, r.players().get(0).score(), DELTA);
  }


}
