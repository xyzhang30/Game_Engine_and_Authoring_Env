package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import oogasalad.model.api.exception.InvalidParameterNumberException;

import java.util.ArrayList;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.command.SetDelayedPointsCommand;
import oogasalad.model.gameparser.ExecutableFactory;
import oogasalad.model.gameparser.GameLoaderModel;
import org.junit.jupiter.api.Test;

public class CommandExceptionTest {

  @Test
  public void testInvalidParamException() {
    assertThrows(InvalidParameterNumberException.class, () -> {
      Command cmd = ExecutableFactory.createCommand("SetDelayedPointsCommand",
          List.of(),
          Map.of());
      GameEngine gameEngine = new GameEngine("badParamNumberMiniGolfTest");
      cmd.execute(gameEngine);
    });
  }
}
