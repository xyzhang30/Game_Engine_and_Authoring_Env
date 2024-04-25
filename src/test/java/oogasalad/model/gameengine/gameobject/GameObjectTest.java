package oogasalad.model.gameengine.gameobject;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import java.util.Optional;
import java.util.function.Supplier;

public class GameObjectTest {

  private GameObject gameObject;

  @Before
  public void setUp() {
    gameObject = new GameObject(1, 1, 1, 1, true, 1, 1, 1, 1, 1, "", false, false); // Create a test
  }

  @Test
  public void testAddStrikeable() {
    Strikeable mockStrikeable = mock(Strikeable.class);
    gameObject.addStrikeable(mockStrikeable);
    Optional<Strikeable> result = gameObject.getStrikeable();
    assertTrue(result.isPresent());
    assertEquals(mockStrikeable, result.get());
  }


  @Test
  public void testCalculateSpeeds() {
    Supplier<List<Double>> mockSpeedCalculator = () -> {
      return List.of(2.0, 3.0);
    };
    gameObject.calculateSpeeds(mockSpeedCalculator);
    assertEquals(2.0, gameObject.toGameObjectRecord().velocityX(), 0.001);
    assertEquals(3.0, gameObject.toGameObjectRecord().velocityY(), 0.001);
  }


  @Test
  public void testGetNextSpeed() {
    Supplier<List<Double>> mockSpeedCalculator = () -> {
      return List.of(2.0, 3.0);
    };
    gameObject.calculateNextSpeeds(mockSpeedCalculator);
    assertEquals(0, gameObject.toGameObjectRecord().velocityX(), 0.001);
    assertEquals(0, gameObject.toGameObjectRecord().velocityY(), 0.001);
    gameObject.updatePostCollisionVelocity();
    assertEquals(2.0, gameObject.toGameObjectRecord().velocityX(), 0.001);
    assertEquals(3.0, gameObject.toGameObjectRecord().velocityY(), 0.001);
  }


  @Test
  public void testStop() {
    Supplier<List<Double>> mockSpeedCalculator = () -> {
      return List.of(2.0, 3.0);
    };
    gameObject.calculateSpeeds(mockSpeedCalculator);
    gameObject.stop();

    assertEquals(0.0, gameObject.toGameObjectRecord().velocityY(), 0.001);
    assertEquals(0.0, gameObject.toGameObjectRecord().velocityX(), 0.001);
  }

  @Test
  public void testTeleportTo() {
    GameObject targetObject = new GameObject(2,10,10, 10, true, 1, 1, 1, 1, 1, "", false, false); // Create a test
    gameObject.teleportTo(targetObject);
    assertEquals(targetObject.getX(), gameObject.getX(), 0.001);
    assertEquals(targetObject.getY(), gameObject.getY(), 0.001);
  }



}