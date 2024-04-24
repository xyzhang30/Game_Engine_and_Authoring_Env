package oogasalad.model.gameengine.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;

public class MultiplySpeedCommandTest {

  @Test
  public void testMultiplySpeedCommand() {
    GameObject gameObject = mock(GameObject.class);
    GameEngine gameEngine = mock(GameEngine.class);
    gameObject.applyInitialVelocity(2,0);
    MultiplySpeedCommand command = new MultiplySpeedCommand(List.of(1,2), Map.of(1, gameObject));
    command.execute(gameEngine);
    verify(gameObject).multiplySpeed(2.0);
  }
}