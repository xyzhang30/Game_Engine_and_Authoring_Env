package oogasalad.model.gameengine.command;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;

public class MoveToStartingStateCommandTest {

  @Test
  public void testExecute() {
    GameObject gameObject = mock(GameObject.class);
    GameEngine engine = mock(GameEngine.class);
    Map<Integer, GameObject> gameObjectMap = Collections.singletonMap(1, gameObject);
    MoveToStartingStateCommand command = new MoveToStartingStateCommand(Arrays.asList(1), gameObjectMap);
    command.execute(engine);
    verify(gameObject, times(1)).toStartingState();
  }
}
