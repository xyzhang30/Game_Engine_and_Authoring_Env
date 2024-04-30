package oogasalad.model.gameengine.gameobject.controllable;

import java.util.List;
import java.util.Map;
import oogasalad.model.api.data.Dimension;
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.api.data.Position;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.controllable.DefaultControllable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControllableTest {

  private DefaultControllable defaultControllable;
  private GameObject gameObject;

  @BeforeEach
  public void setUp() {
    gameObject = new GameObject(new GameObjectProperties(1, List.of("visible", "strikeable"), 1.0
        , new Position(1.0,1.0), "", new Dimension(1,1), Map.of("Default", List.of()), 1, 1, 1,
        Map.of("Default", ""),
        1.0, false,
        false,0)); //
    gameObject.addControllable();
    defaultControllable = new DefaultControllable(gameObject);
  }

  @Test
  public void testMoveXPositive() {
    defaultControllable.setMovement(2,0);
    double result = defaultControllable.moveX(true);
    assertEquals(2.0, result);
  }

  @Test
  public void testMoveXNegative() {
    defaultControllable.setMovement(3,0);
    double result = defaultControllable.moveX(false);
    assertEquals(-3.0, result);
  }
  @Test
  public void testAsGameObject() {
    GameObject result = defaultControllable.asGameObject();
    assertEquals(gameObject, result);
  }

  @Test
  public void testSetMovement() {
    defaultControllable.setMovement(2,0);
    assertEquals(2, defaultControllable.moveX(true));
  }
}
