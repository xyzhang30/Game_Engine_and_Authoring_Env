package oogasalad.model.gameengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.model.gameengine.collidable.GameObjectContainer;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

public class MiniGolfGameRulesTest {


  private GameEngine gameEngine;
  private GameObjectContainer container;

  private static final double DELTA = .0001;

  private static  final String TITLE = "testMiniGolfRules";

  @BeforeEach
  public void setUp() {
    gameEngine = new GameEngine(TITLE);
    container = gameEngine.getCollidableContainer();
  }

  private boolean isStatic(GameRecord r) {
    for(GameObjectRecord cr : r.collidables()) {
      if(cr.visible() && (cr.velocityY()!=0 || cr.velocityX()!=0)) {
        return false;
      }
    }
    return true;
  }

  @Test
  public void testRoundOfGolf() {
    gameEngine.applyInitialVelocity(0, 0, 1);
    assertEquals(1.0,gameEngine.getTurn(), DELTA);
    gameEngine.update(1);
    assertEquals(2.0,gameEngine.getTurn(), DELTA);
    gameEngine.applyInitialVelocity(25, 5*Math.PI/4, 2);
    GameRecord r = gameEngine.update(1);
    gameEngine.applyInitialVelocity(1, 0, 1);
    gameEngine.update(1);
    assertEquals(1.0,gameEngine.getTurn(), DELTA);
    gameEngine.applyInitialVelocity(1, 0, 1);
    gameEngine.update(1);
    assertEquals(1.0,gameEngine.getTurn(), DELTA);
    gameEngine.applyInitialVelocity(1, 0, 1);
    GameRecord r2 = gameEngine.update(1);
    assertEquals(1.0,gameEngine.getTurn(), DELTA);
    gameEngine.applyInitialVelocity(30, Math.PI/4, 1);
    GameRecord r3 = gameEngine.update(1);
    assertEquals(2.0, gameEngine.getRound(), DELTA);
  }

  @Test
  public void testTwoHoles() {
    gameEngine.applyInitialVelocity(0, 0, 1);
    gameEngine.update(1);
    gameEngine.applyInitialVelocity(25, 5*Math.PI/4, 2);
    gameEngine.update(1);
    gameEngine.applyInitialVelocity(25, Math.PI/4, 1);
    gameEngine.update(1);
    gameEngine.applyInitialVelocity(0, 0, 1);
    gameEngine.update(1);
    gameEngine.applyInitialVelocity(25, 5*Math.PI/4, 2);
    gameEngine.update(1);
    gameEngine.applyInitialVelocity(25, Math.PI/4, 1);
    gameEngine.update(1);
    // updates ideally
    assertEquals(3.0, gameEngine.getRound(), DELTA);
    assertTrue( gameEngine.isOver());
  }

}
