package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import oogasalad.model.api.exception.InvalidParameterNumberException;

import java.util.ArrayList;
import oogasalad.model.api.exception.InvalidParameterNumberException;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.command.AddDelayedPointsCommand;
import oogasalad.model.gameparser.GameLoaderModel;
import org.junit.jupiter.api.Test;

public class CommandExceptionTest {

  @Test
  public void testMissingParamAdjustPointsCommand() {
    assertThrows(InvalidParameterNumberException.class, () -> {
      AddDelayedPointsCommand cmd = new AddDelayedPointsCommand(new ArrayList<>());
      GameEngine gameEngine = new GameEngine("badParamNumberMiniGolfTest");
      cmd.execute(gameEngine);
      GameLoaderModel loader = new GameLoaderModel("badParamNumberMiniGolfTest");
      loader.prepareRound(1);
    });
  }
}
