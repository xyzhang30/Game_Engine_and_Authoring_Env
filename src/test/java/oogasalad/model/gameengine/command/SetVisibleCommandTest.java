package oogasalad.model.gameengine.command;

import static org.mockito.Mockito.*;

import java.util.Collections;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.command.SetVisibleCommand;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.GameObjectContainer;

public class SetVisibleCommandTest {

  @Test
  public void testSetVisible() {
    GameObject gameObject = mock(GameObject.class);
    GameObjectContainer gameObjectContainer = mock(GameObjectContainer.class);

    GameEngine gameEngine = mock(GameEngine.class);
    when(gameEngine.getGameObjectContainer()).thenReturn(gameObjectContainer);

    List<Double> arguments = List.of(1.0, 1.0); // Assuming GameObject ID is 1 and visibility
    SetVisibleCommand command = new SetVisibleCommand(List.of(1,1), Map.of(1,gameObject));
    command.execute(gameEngine);

    verify(gameObject).setVisible(true);
    List<Double> arguments2 = List.of(1.0, 0.0); // Assuming GameObject ID is 1 and visibility
    SetVisibleCommand command2 = new SetVisibleCommand(List.of(1,0), Map.of(1, gameObject));
    command2.execute(gameEngine);
    verify(gameObject).setVisible(false);
  }
}
