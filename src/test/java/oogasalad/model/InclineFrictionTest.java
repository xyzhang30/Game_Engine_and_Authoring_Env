package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import oogasalad.model.api.CollidableRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InclineFrictionTest {

  private GameEngine gameEngine;
  private CollidableContainer container;

  private static final double DELTA = .0001;

  @BeforeEach
  public void setUp() {
    gameEngine = new GameEngine("testFrictionAndGravity");
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
  public void testBallAcceleratingDownIncline() {
    //angle in JSON set to 60 degrees
    double initialVelocity = 10.0;  // Start from a small tap
    double angleOfShot = Math.toRadians(90);  // Shot angle is horizontal but will not affect since initial velocity is zero

    gameEngine.applyInitialVelocity(initialVelocity, angleOfShot, 1);
    System.out.println("Ball record before: " + container.getCollidableRecord(1));

    // Simulate a short time step to observe acceleration due to incline
    gameEngine.update(0.0167);
    CollidableRecord ballRecord = container.getCollidableRecord(1);
    System.out.println("Ball record after: " + ballRecord);

    // We expect the velocity to increase due to the incline
    assertTrue(ballRecord.velocityY() > initialVelocity, "Velocity should increase due to gravity along the incline");
  }

}
