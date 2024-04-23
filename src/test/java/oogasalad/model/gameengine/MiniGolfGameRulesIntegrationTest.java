package oogasalad.model.gameengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import oogasalad.model.api.GameRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MiniGolfGameRulesIntegrationTest {


  private static final double DELTA = .0001;
  private static final String TITLE = "testMiniGolfRules";
  private GameEngine gameEngine;

  @BeforeEach
  public void setUp() {
    gameEngine = new GameEngine(TITLE);
  }

  @Test
  public void testRoundOfGolf() {
    gameEngine.applyInitialVelocity(0, 0);
    assertEquals(1.0, gameEngine.restoreLastStaticGameRecord().turn(), DELTA);
    gameEngine.update(1);
    assertEquals(2.0, gameEngine.restoreLastStaticGameRecord().turn(), DELTA);
    gameEngine.applyInitialVelocity(25, 5 * Math.PI / 4);
    GameRecord r = gameEngine.update(1);
    gameEngine.applyInitialVelocity(1, 0);
    gameEngine.update(1);
    assertEquals(1.0, gameEngine.restoreLastStaticGameRecord().turn(), DELTA);
    gameEngine.applyInitialVelocity(1, 0);
    gameEngine.update(1);
    assertEquals(1.0, gameEngine.restoreLastStaticGameRecord().turn(), DELTA);
    gameEngine.applyInitialVelocity(1, 0);
    GameRecord r2 = gameEngine.update(1);
    assertEquals(1.0, gameEngine.restoreLastStaticGameRecord().turn(), DELTA);
    gameEngine.applyInitialVelocity(30, Math.PI / 4);
    GameRecord r3 = gameEngine.update(1);
    assertEquals(2.0, gameEngine.restoreLastStaticGameRecord().round(), DELTA);
  }

  @Test
  public void testTwoHoles() {
    gameEngine.applyInitialVelocity(0, 0);
    gameEngine.update(1);
    gameEngine.applyInitialVelocity(25, 5 * Math.PI / 4);
    gameEngine.update(1);
    gameEngine.applyInitialVelocity(25, Math.PI / 4);
    gameEngine.update(1);
    gameEngine.applyInitialVelocity(0, 0);
    gameEngine.update(1);
    gameEngine.applyInitialVelocity(25, 5 * Math.PI / 4);
    gameEngine.update(1);
    gameEngine.applyInitialVelocity(25, Math.PI / 4);
    gameEngine.update(1);
    // updates ideally
    assertEquals(3.0, gameEngine.restoreLastStaticGameRecord().round(), DELTA);
  }

}
