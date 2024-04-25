package oogasalad.model.gameengine.condition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;

public class GameObjectsNotVisibleConditionTest {

  private GameObjectsNotVisibleCondition condition;
  private GameObject obj1;
  private GameObject obj2;
  private GameObject obj3;
  private Map<Integer,GameObject> gameObjectMap;

  @BeforeEach
  public void setUp() {
     obj1 = mock(GameObject.class);
     obj2 = mock(GameObject.class);
     obj3 = mock(GameObject.class);
     gameObjectMap = Map.of(1,obj1,2,obj2,3,obj3);
  }

  @Test
  public void testEvaluateAllNotVisible() {
    when(obj1.getVisible()).thenReturn(true);
    when(obj2.getVisible()).thenReturn(false);
    when(obj3.getVisible()).thenReturn(false);
    condition = new GameObjectsNotVisibleCondition(List.of(2,3),gameObjectMap);
    assertTrue(condition.evaluate(mock(GameEngine.class)));
  }

  @Test
  public void testEvaluateSomeVisible() {
      when(obj1.getVisible()).thenReturn(true);
      when(obj2.getVisible()).thenReturn(false);
      when(obj3.getVisible()).thenReturn(false);
      condition = new GameObjectsNotVisibleCondition(List.of(1,2,3),gameObjectMap);
      assertFalse(condition.evaluate(mock(GameEngine.class)));
  }
}
