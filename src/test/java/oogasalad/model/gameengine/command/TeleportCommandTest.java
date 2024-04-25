package oogasalad.model.gameengine.command;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;

public class TeleportCommandTest {

  @Test
  public void teleportCommandTest() {
    GameObject gameObjectFrom = mock(GameObject.class);
    GameObject gameObjectTo = mock(GameObject.class);
    GameEngine engine = mock(GameEngine.class);
    Map<Integer, GameObject> gameObjectMap = Map.of(1, gameObjectFrom, 2, gameObjectTo);

    TeleportCommand command = new TeleportCommand(Arrays.asList(1, 2), gameObjectMap);

    command.execute(engine);

    verify(gameObjectFrom, times(1)).teleportTo(gameObjectTo);
  }
}




