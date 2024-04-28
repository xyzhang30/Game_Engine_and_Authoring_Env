package oogasalad.model.gameengine.command;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;

public class SetVisibleCommandTest {

  @Test
  public void testSetVisible() {
    GameObject gameObject = mock(GameObject.class);
    GameEngine gameEngine = mock(GameEngine.class);
    SetVisibleCommand command = new SetVisibleCommand(List.of(1,1), Map.of(1,gameObject));
    command.execute(gameEngine);
    verify(gameObject).setVisible(true);
    SetVisibleCommand command2 = new SetVisibleCommand(List.of(1,0), Map.of(1, gameObject));
    command2.execute(gameEngine);
    verify(gameObject).setVisible(false);
  }
}
