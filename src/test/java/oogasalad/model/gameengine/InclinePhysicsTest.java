package oogasalad.model.gameengine;

import static org.junit.jupiter.api.Assertions.assertTrue;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.model.gameengine.gameobject.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InclinePhysicsTest {


  private static final double DELTA = .0001;
  private GameEngine gameEngine;
  private List<GameObject> gameObjects;

  @BeforeEach
  public void setUp() {
    gameEngine = new GameEngine("testIncline");
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
  public void testBallAcceleratingDownIncline1() {
    //angle in JSON set to 80 degrees
    double initialVelocity = 8.75;  // Start from a small tap
    double angleOfShot = Math.toRadians(90);  // Shot angle is horizontal but will not affect since initial velocity is zero
    gameEngine.applyInitialVelocity(initialVelocity, angleOfShot);
    // Simulate a short time step to observe acceleration due to incline
    gameEngine.update(0.01);
    // We expect the velocity to increase due to the incline
    assertTrue(getRecord(1).velocityY() > initialVelocity, "Velocity should increase due to "
        + "gravity along "
        + "the incline");
  }

  @Test
  public void testBallAcceleratingDownIncline2() {
    //angle in JSON set to 60 degrees
    double initialVelocity = 10.0;  // Start from a small tap
    double angleOfShot = Math.toRadians(-90);  // Shot angle is horizontal but will not affect since initial velocity is zero
    gameEngine.applyInitialVelocity(initialVelocity, angleOfShot);
    //System.out.println("Ball record before: " + container.getCollidableRecord(1));
    // Simulate a short time step to observe acceleration due to incline
    gameEngine.update(0.0167);
    //System.out.println("Ball record after: " + ballRecord);
    // We expect the velocity to increase due to the incline
    assertTrue(getRecord(2).velocityY() < initialVelocity, "Velocity should decrease due to "
        + "gravity along "
        + "the incline");
  }

  @Test
  public void testBallAcceleratingDownIncline3() {
    //angle in JSON set to 60 degrees
    double initialVelocity = 10.0;  // Start from a small tap
    double angleOfShot = Math.toRadians(0);  // Shot angle is horizontal but will not affect since initial velocity is zero
    gameEngine.applyInitialVelocity(initialVelocity, angleOfShot);
    //System.out.println("Ball record before: " + container.getCollidableRecord(1));
    // Simulate a short time step to observe acceleration due to incline
    gameEngine.update(0.0167);
    //System.out.println("Ball record after: " + ballRecord);
    // We expect the velocity to increase due to the incline
    assertTrue(getRecord(3).velocityX() > initialVelocity, "Velocity should increase due to "
        + "gravity along "
        + "the incline");
  }

  @Test
  public void testBallAcceleratingDownIncline4() {
    //angle in JSON set to 60 degrees
    double initialVelocity = 10.0;  // Start from a small tap
    double angleOfShot = Math.toRadians(-180);  // Shot angle is horizontal but will not affect since initial velocity is zero
    gameEngine.applyInitialVelocity(initialVelocity, angleOfShot);
    //System.out.println("Ball record before: " + container.getCollidableRecord(1));
    // Simulate a short time step to observe acceleration due to incline
    gameEngine.update(0.0167);
    //System.out.println("Ball record after: " + ballRecord);
    // We expect the velocity to increase due to the incline
    assertTrue(getRecord(4).velocityY() < initialVelocity, "Velocity should decrease due to "
        + "gravity along "
        + "the incline");
  }

}

