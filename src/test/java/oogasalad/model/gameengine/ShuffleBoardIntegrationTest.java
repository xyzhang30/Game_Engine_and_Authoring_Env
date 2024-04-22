package oogasalad.model.gameengine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.gameobject.GameObjectContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ShuffleBoardIntegrationTest {


  private static final double DELTA = .0001;
  private static final String TITLE = "shuffleTest";
  private GameEngine gameEngine;
  private GameObjectContainer container;

  private boolean isStatic(GameRecord r) {
    for (GameObjectRecord cr : r.gameObjectRecords()) {
      if (cr.visible() && (cr.velocityY() != 0 || cr.velocityX() != 0)) {
        return false;
      }
    }
    return true;
  }

  @BeforeEach
  public void setUp() {
    gameEngine = new GameEngine(TITLE);

    container = gameEngine.getGameObjectContainer();

  }


  // Test method with parameterized IDs
  @ParameterizedTest
  @ValueSource(ints = {10, 11, 12, 13})
  public void testDelayedScoring(int shuffleValue) {
    // Assuming velocity and angle are constant
    double velocity = 120;
    double angle = -Math.PI / 2;
    gameEngine.applyInitialVelocity(velocity, angle, 12);
    shuffleRun(12);
  }

  @Test
  public void testFullRound() {
    // Assuming velocity and angle are constant
    double angle = -Math.PI / 2;
    gameEngine.applyInitialVelocity(.01, angle, 10);
    shuffleRun(10);
    gameEngine.applyInitialVelocity(.01, angle, 12);
    shuffleRun(12);
    gameEngine.applyInitialVelocity(.01, angle, 11);
    shuffleRun(11);
    gameEngine.applyInitialVelocity(.01, angle, 13);
    shuffleRun(13);
    assertEquals(1, gameEngine.restoreLastStaticGameRecord().players().get(0).score());
    assertEquals(5, gameEngine.restoreLastStaticGameRecord().players().get(1).score());
    assertEquals(2, gameEngine.restoreLastStaticGameRecord().round());
  }


  private void shuffleRun(int id) {
    GameRecord gr = gameEngine.update(.05);
    System.out.println(gr);
    while (!isStatic(gr)) {
      gr = gameEngine.update(.05);
      for (GameObjectRecord r : gr.gameObjectRecords()) {
        if (r.id() == id) {
          scoreTestHelper(r, gr.players().get(id <= 11 ? 0 : 1));
        }
      }
    }
  }

  private void scoreTestHelper(GameObjectRecord r, PlayerRecord p) {
    System.out.println(r.y() + " " + (p.score()));
    if (r.y() < 65) {
      assertEquals(0, p.score());
    } else if (r.y() < 250) {
      assertEquals(3, p.score());
    } else if (r.y() < 450) {
      assertEquals(2, p.score());
    } else if (r.y() < 650) {
      assertEquals(1, p.score());
    } else {
      assertEquals(0
          , p.score());
    }
  }


}
