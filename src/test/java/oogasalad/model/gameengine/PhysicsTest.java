package oogasalad.model.gameengine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.model.gameengine.gameobject.GameObjectContainer;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

public class PhysicsTest {


  private GameEngine gameEngine;
  private GameObjectContainer container;

  private static final double DELTA = .0001;


  @BeforeEach
  public void setUp() {
    gameEngine = new GameEngine("testPhysics");
    container = gameEngine.getGameObjectContainer();
  }

  private boolean isStatic(GameRecord r) {
    for(GameObjectRecord cr : r.gameObjectRecords()) {
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
    assertEquals(10, container.getGameObjectRecord(1).velocityX());
    assertEquals(1.0, gameEngine.getPlayerContainer().getPlayerRecords().get(0).score(), DELTA);

  }

  @Test
  public void testSingularUpdate() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(10, 0, 1);
    // Assert that the initial round and turn are as expected
    assertEquals(10, container.getGameObjectRecord(1).velocityX());
    gameEngine.update(1.0/4.0);
    assertEquals(2.5, container.getGameObjectRecord(1).x(), DELTA);
    assertEquals(5, container.getGameObjectRecord(1).velocityX(), DELTA);
  }


  @Test
  public void testMultipleUpdate() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(15, 0, 1);
    assertEquals(15, container.getGameObjectRecord(1).velocityX(), DELTA);
    gameEngine.update(1.0/4);
    assertEquals(15/4.0, container.getGameObjectRecord(1).x(), DELTA);
    assertEquals(10, container.getGameObjectRecord(1).velocityX(), DELTA);
    gameEngine.update(1.0/4);
    assertEquals(25/4.0, container.getGameObjectRecord(1).x(), DELTA);
    assertEquals(5, container.getGameObjectRecord(1).velocityX(), DELTA);
  }


  /**
  @Test
  public void testStop() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(15, Math.PI/2, 1);
    System.out.println(gameEngine.getCollidableContainer().getCollidableRecord(1));
    GameRecord r = gameEngine.update(1.0/4);
    System.out.println(r.gameObjectRecords().get(0));
    while(!isStatic(r)) {
      r = gameEngine.update(1.0/4);
      System.out.println(r.gameObjectRecords().get(0));

    }
    assertEquals(7.5, container.getCollidableRecord(1).y(), DELTA);
    assertEquals(0, container.getCollidableRecord(1).velocityY(), DELTA);
  }
*/

  @Test
  public void testMoveAtAngle() {
    gameEngine.applyInitialVelocity(20, Math.PI/4, 1);
    gameEngine.update(.5);
    System.out.println(container.getGameObjectRecord(1));
    assertEquals(10/Math.sqrt(2), container.getGameObjectRecord(1).x(), DELTA);
    assertEquals(10/Math.sqrt(2), container.getGameObjectRecord(1).y(), DELTA);
    assertEquals(10/Math.sqrt(2), container.getGameObjectRecord(1).velocityX(), DELTA);
    assertEquals(10/Math.sqrt(2), container.getGameObjectRecord(1).velocityX(), DELTA);
  }

  @Test
  public void testTwoMovingObjectsCollide() {
    gameEngine.getGameObjectContainer().getGameObject(1).setVisible(true);
    gameEngine.getGameObjectContainer().getGameObject(10).setVisible(true);
    gameEngine.applyInitialVelocity(15, -Math.PI, 1);
    gameEngine.applyInitialVelocity(15, 0, 10);
    gameEngine.getGameObjectContainer().getGameObject(1).setVisible(true);
    gameEngine.getGameObjectContainer().getGameObject(10).setVisible(true);
    gameEngine.update(.25);
    assertEquals(-10, container.getGameObjectRecord(1).velocityX(), DELTA);
    assertEquals(10, container.getGameObjectRecord(10).velocityX(), DELTA);
    gameEngine.update(.25);
    assertEquals(-5, container.getGameObjectRecord(1).velocityX(), DELTA);
    assertEquals(5, container.getGameObjectRecord(10).velocityX(), DELTA);
  }




  @Test
  public void testAdjustPointsCommand() {
    gameEngine.getGameObjectContainer().getGameObject(1).setVisible(true);
    gameEngine.getGameObjectContainer().getGameObject(10).setVisible(true);
    gameEngine.applyInitialVelocity(20, -Math.PI, 1);
    gameEngine.applyInitialVelocity(20, 0, 10);
    gameEngine.applyInitialVelocity(20, -Math.PI, 1);
    GameRecord r = gameEngine.update(1);

    System.out.println(gameEngine.getGameObjectContainer().getGameObject(1).toGameObjectRecord().x());
    System.out.println(gameEngine.getGameObjectContainer().getGameObject(10).toGameObjectRecord().x());

    assertEquals(2.0, r.players().get(0).score(), DELTA);
  }


  @Test
  public void testUndoCommand() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(100, 0, 1);
    gameEngine.update(1);

    assertEquals(0.0,container.getGameObjectRecord(1).velocityX(), DELTA);
    assertEquals(0.0,container.getGameObjectRecord(1).velocityY(), DELTA);
    assertEquals(0.0,container.getGameObjectRecord(1).x(), DELTA);
    assertEquals(0.0,container.getGameObjectRecord(1).y(), DELTA);
  }




  @Test
  public void testAdvanceTurnAndAdjustPoints() {
    gameEngine.applyInitialVelocity(1, 0, 1);
    assertEquals(1.0,gameEngine.getTurn(), DELTA);
    gameEngine.update(1);
    assertEquals(2.0,gameEngine.getTurn(), DELTA);
    gameEngine.applyInitialVelocity(1, 0, 6);
    gameEngine.update(1);
    assertEquals(1.0,gameEngine.getTurn(), DELTA);
    GameRecord r = gameEngine.update(1);
    //note the ball belongs to player 1 so they get all the points
    assertEquals(1.0, r.players().get(0).score(), DELTA);
  }


}
