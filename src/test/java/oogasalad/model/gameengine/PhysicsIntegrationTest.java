package oogasalad.model.gameengine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.model.gameengine.gameobject.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhysicsIntegrationTest {


  private static final double DELTA = .0001;
  private GameEngine gameEngine;
  private Collection<GameObject> gameObjects;
  @BeforeEach
  public void setUp() {
    gameEngine = new GameEngine("data/playable_games/testPhysics");
    gameObjects = gameEngine.getGameObjects();
  }


  private GameObjectRecord getRecord(int id) {
    for(GameObject o : gameObjects) {
      if(o.getId()==id) {
        return o.toGameObjectRecord();
      }
    }
    return null;
  }

  private boolean isStatic(GameRecord r) {
    for (GameObjectRecord cr : r.gameObjectRecords()) {
      if (cr.velocityY() != 0 || cr.velocityX() != 0) {
        return false;
      }
    }
    return true;
  }

  @Test
  public void testStartAndResetGame() {
    // Ensure the game starts without errors

    // Assert that the initial round and turn are as expected
    assertEquals(1, gameEngine.restoreLastStaticGameRecord().round());
    assertEquals(1, gameEngine.restoreLastStaticGameRecord().turn());
    // Reset the game and verify that it's back to initial state
    gameEngine.reset();
    assertEquals(1, gameEngine.restoreLastStaticGameRecord().round());
    assertEquals(1, gameEngine.restoreLastStaticGameRecord().turn());
  }


  @Test
  public void testOnApplyVelocity() {
    // Ensure the game starts without errors

    gameEngine.applyInitialVelocity(10, 0);
    // Assert that the initial round and turn are as expected
    assertEquals(10, getRecord(1).velocityX());
    assertEquals(1.0,
        gameEngine.getPlayerContainer().getPlayers().iterator().next().getPlayerRecord().score(),
        DELTA);

  }

  @Test
  public void testSingularUpdate() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(10, 0);
    // Assert that the initial round and turn are as expected
    assertEquals(10, getRecord(1).velocityX());
    gameEngine.update(1.0 / 4.0);
    assertEquals(2.5, getRecord(1).x(), DELTA);
    assertEquals(5, getRecord(1).velocityX(), DELTA);
  }


  @Test
  public void testMultipleUpdate() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(15, 0);
    assertEquals(15, getRecord(1).velocityX(), DELTA);
    gameEngine.update(1.0 / 4);
    assertEquals(15 / 4.0, getRecord(1).x(), DELTA);
    assertEquals(10, getRecord(1).velocityX(), DELTA);
    gameEngine.update(1.0 / 4);
    assertEquals(25 / 4.0, getRecord(1).x(), DELTA);
    assertEquals(5, getRecord(1).velocityX(), DELTA);
  }


  @Test
  public void testStop() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(15, Math.PI / 2);
    GameRecord r = gameEngine.update(1.0 / 4);
    System.out.println(r.gameObjectRecords().get(0));
    while (!isStatic(r)) {
      r = gameEngine.update(1.0 / 4);
      System.out.println(r.gameObjectRecords().get(0));

    }
    assertEquals(7.5, getRecord(1).y(), DELTA);
    assertEquals(0, getRecord(1).velocityY(), DELTA);
  }

  @Test
  public void testMoveAtAngle() {
    gameEngine.applyInitialVelocity(20, Math.PI /4);
    gameEngine.update(.5);
    System.out.println(getRecord(1));
    assertEquals(10 / Math.sqrt(2), getRecord(1).x(), DELTA);
    assertEquals(10 / Math.sqrt(2), getRecord(1).y(), DELTA);
    assertEquals(10 / Math.sqrt(2), getRecord(1).velocityX(), DELTA);
    assertEquals(10 / Math.sqrt(2), getRecord(1).velocityX(), DELTA);
  }

  @Test
  public void testTwoMovingObjectsCollide() {
    gameEngine.applyInitialVelocity(15, -Math.PI);
    for(GameObject o:gameEngine.getGameObjects()) {
      if(o.getId()==10) {
        o.setVisible(true);
      }
    }
    gameEngine.update(.25);
    assertEquals(-3.33333, getRecord(1).velocityX(), DELTA);
    assertEquals(-13.3333, getRecord(10).velocityX(), DELTA);
  }


  @Test
  public void testAdjustPointsCommand() {

    for(GameObject o:gameEngine.getGameObjects()) {
      if(o.getId()==10) {
        o.setVisible(true);
      }
    }
    gameEngine.applyInitialVelocity(20, -Math.PI);
    GameRecord r = gameEngine.update(1);
    System.out.println(r.players());
    gameEngine.applyInitialVelocity(20, 0);
    gameEngine.applyInitialVelocity(20, -Math.PI);
    GameRecord r2 = gameEngine.update(10);
    assertEquals(2.0, r2.players().get(1).score(), DELTA);
  }


  @Test
  public void testUndoCommand() {
    // Ensure the game starts without errors
    gameEngine.applyInitialVelocity(100, 0);
    gameEngine.update(1);

    assertEquals(0.0, getRecord(1).velocityX(), DELTA);
    assertEquals(0.0, getRecord(1).velocityY(), DELTA);
    assertEquals(0.0, getRecord(1).x(), DELTA);
    assertEquals(0.0, getRecord(1).y(), DELTA);
  }


  @Test
  public void testAdvanceTurnAndAdjustPoints() {
    gameEngine.applyInitialVelocity(1, 0);
    assertEquals(1.0, gameEngine.restoreLastStaticGameRecord().turn(), DELTA);
    gameEngine.update(1);
    assertEquals(2.0, gameEngine.restoreLastStaticGameRecord().turn(), DELTA);
    gameEngine.applyInitialVelocity(1, 0);
    gameEngine.update(1);
    assertEquals(1.0, gameEngine.restoreLastStaticGameRecord().turn(), DELTA);
    GameRecord r = gameEngine.update(1);
    System.out.println(r.players());
    //note the ball belongs to player 1 so they get all the points
    assertEquals(1.0, r.players().get(0).score(), DELTA);
  }


}
