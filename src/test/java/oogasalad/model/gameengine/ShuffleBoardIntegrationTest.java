package oogasalad.model.gameengine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ShuffleBoardIntegrationTest {


  private static final double DELTA = .0001;
  private static final String TITLE = "data/playable_games/shuffleTest";
  private GameEngine gameEngine;

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
  }

  @Test
  public void testFullRound() {
    // Assuming velocity and angle are constant
    double angle = -Math.PI / 2;
    gameEngine.applyInitialVelocity(.01, angle);
    shuffleRun(10);
    gameEngine.applyInitialVelocity(.01, angle);
    shuffleRun(12);
    gameEngine.applyInitialVelocity(.01, angle);
    shuffleRun(11);
    gameEngine.applyInitialVelocity(.01, angle);
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
  }


}
