package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import oogasalad.model.api.exception.InvalidParameterNumberException;

import java.util.ArrayList;
import oogasalad.model.api.exception.InvalidParameterNumberException;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.command.SetDelayedPointsCommand;
import oogasalad.model.gameparser.GameLoaderModel;
import org.junit.jupiter.api.Test;

public class CommandExceptionTest {

  @Test
  public void testMissingParamAdjustPointsCommand() {
    assertThrows(InvalidParameterNumberException.class, () -> {
      SetDelayedPointsCommand cmd = new SetDelayedPointsCommand(new ArrayList<>(), Map.of());
      GameEngine gameEngine = new GameEngine("badParamNumberMiniGolfTest");
      cmd.execute(gameEngine);
      GameLoaderModel loader = new GameLoaderModel("badParamNumberMiniGolfTest");
      loader.prepareRound(1);
    });
  }
}
