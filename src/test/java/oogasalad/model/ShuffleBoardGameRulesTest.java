package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import oogasalad.model.api.CollidableRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameparser.GameLoaderModel;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ShuffleBoardGameRulesTest {


  private GameEngine gameEngine;
  private CollidableContainer container;

  private static final double DELTA = .0001;

  private static  final String TITLE = "testShuffleboardRules";

  private boolean isStatic(GameRecord r) {
    for(CollidableRecord cr : r.collidables()) {
      if(cr.visible() && (cr.velocityY()!=0 || cr.velocityX()!=0)) {
        return false;
      }
    }
    return true;
  }

  @BeforeEach
  public void setUp() {
    gameEngine = new GameEngine(TITLE);

    container = gameEngine.getCollidableContainer();

  }


  // Test method with parameterized IDs
  @ParameterizedTest
  @ValueSource(ints = {10, 11, 12, 13})
  public void testDelayedScoring(int shuffleValue) {
    // Assuming velocity and angle are constant
    double velocity = 100;
    double angle = -Math.PI/2;
    gameEngine.applyInitialVelocity(velocity, angle, shuffleValue);
    shuffleRun(shuffleValue,0);
  }

  @Test
  public void testFullRound() {
    // Assuming velocity and angle are constant
    double velocity = 100;
    double angle = -Math.PI/2;
    gameEngine.applyInitialVelocity(velocity, angle, 10);
    shuffleRun(10,0);
    System.out.println(gameEngine.getPlayerContainer().getPlayerRecords());
    gameEngine.applyInitialVelocity(velocity, angle, 12);
    shuffleRun(12,0);
    System.out.println(gameEngine.getPlayerContainer().getPlayerRecords());
    gameEngine.applyInitialVelocity(velocity, angle, 11);
    shuffleRun(11, 1);
    gameEngine.applyInitialVelocity(velocity, angle, 13);
    shuffleRun(13, 3);
    System.out.println(container.getCollidableRecords());
    assertEquals(3,gameEngine.getGameRecord().players().get(0).score());
    assertEquals(3,gameEngine.getGameRecord().players().get(1).score());
  }


  private void shuffleRun(int id, int add) {
    GameRecord gr = gameEngine.update(.05);
    while(!isStatic(gr)) {
      gr = gameEngine.update(.05);
      for(CollidableRecord r : gr.collidables()) {
        if(r.id() == id) {
          scoreTestHelper(r, gr.players().get(0), add);
        }
      }
    }
  }

  private void scoreTestHelper(CollidableRecord r, PlayerRecord p, int add) {
    System.out.println("" + r.y() + " " + p.score());
    if(r.y() < 65) {
      assertEquals(0+add,p.score());
    }
    else if(r.y() < 250) {
      assertEquals(3+add,p.score());
    }
    else if(r.y() < 450) {
      assertEquals(2+add,p.score());
    }
    else if(r.y() < 650) {
      assertEquals(1+add,p.score());
    }
    else  {
      assertEquals(0+add
          ,p.score());
    }
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
