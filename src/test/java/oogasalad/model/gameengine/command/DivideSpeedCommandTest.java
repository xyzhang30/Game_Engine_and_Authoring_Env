package oogasalad.model.gameengine.command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;

public class DivideSpeedCommandTest {

  @Test
  public void testConstructor_ValidArguments() {
    GameObject gameObject = mock(GameObject.class);
    Map<Integer, GameObject> gameObjectMap = Collections.singletonMap(1, gameObject);
    DivideSpeedCommand command = new DivideSpeedCommand(Arrays.asList(1, 2), gameObjectMap);
    assertNotNull(command);
  }

  @Test
  public void testConstructor_InvalidArguments() {
    GameObject gameObject = mock(GameObject.class);
    Map<Integer, GameObject> gameObjectMap = Collections.singletonMap(1, gameObject);
    assertThrows(IllegalArgumentException.class, () -> {
      new DivideSpeedCommand(Arrays.asList(1, 0), gameObjectMap);
    });
  }

  @Test
  public void testExecute() {
    GameObject gameObject = mock(GameObject.class);
    Map<Integer, GameObject> gameObjectMap = Collections.singletonMap(1, gameObject);
    DivideSpeedCommand command = new DivideSpeedCommand(Arrays.asList(1, 2), gameObjectMap);
    GameEngine engine = mock(GameEngine.class);
    when(gameObject.getId()).thenReturn(1);
    command.execute(engine);
    verify(gameObject, times(1)).multiplySpeed(0.5); // 10.0 / 2 = 5.0
  }
}
