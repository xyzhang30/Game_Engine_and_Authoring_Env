package oogasalad.model.gameengine.gameobject;

import oogasalad.model.gameengine.gameobject.DefaultStrikeable;
import oogasalad.model.gameengine.gameobject.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StrikeableTest {

  private DefaultStrikeable defaultStrikeable;
  private GameObject gameObject;

  @BeforeEach
  public void setUp() {
    gameObject = new GameObject(1, 1, 1, 1, true, 1, 1, 1, 1, 1, "", false, false); // Create a test
    defaultStrikeable = new DefaultStrikeable(gameObject);
  }

  @Test
  public void testApplyInitialVelocity() {
    defaultStrikeable.applyInitialVelocity(5.0, Math.PI / 4); // 45 degrees
    double speedX = gameObject.toGameObjectRecord().velocityX();
    double speedY = gameObject.toGameObjectRecord().velocityY();
    assertEquals(5.0 * Math.cos(Math.PI / 4), speedX);
    assertEquals(5.0 * Math.sin(Math.PI / 4), speedY);
  }

  @Test
  public void testAsGameObject() {
    GameObject result = defaultStrikeable.asGameObject();
    assertEquals(gameObject, result);
  }
}
