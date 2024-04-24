package oogasalad.model.gameengine.scoreable;

import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.scoreable.DefaultScoreable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreableTest {

  private DefaultScoreable defaultScoreable;
  private GameObject gameObject;

  @BeforeEach
  public void setUp() {
    gameObject = new GameObject(1, 1, 1, 1, true, 1, 1, 1, 1, 1, "", false, false); // Create a test
    defaultScoreable = new DefaultScoreable(gameObject);
    gameObject.addScoreable(defaultScoreable);
  }

  @Test
  public void testSetAndGetTemporaryScore() {
    defaultScoreable.setTemporaryScore(10.0);
    assertEquals(10.0, defaultScoreable.getTemporaryScore());
  }

  @Test
  public void testIncrementTemporaryScorePositive() {
    defaultScoreable.setTemporaryScore(3.0);
    defaultScoreable.incrementTemporaryScore(2.0);
    assertEquals(5.0, defaultScoreable.getTemporaryScore());
    defaultScoreable.setTemporaryScore(3.0);
    assertEquals(3.0, defaultScoreable.getTemporaryScore());
  }

  @Test
  public void testIncrementTemporaryScoreNegative() {
    defaultScoreable.setTemporaryScore(5.0);
    defaultScoreable.incrementTemporaryScore(-3.0);
    assertEquals(2.0, defaultScoreable.getTemporaryScore());
  }

  @Test
  public void testAsGameObject() {
    GameObject result = defaultScoreable.asGameObject();
    assertEquals(gameObject, result);
  }

}
