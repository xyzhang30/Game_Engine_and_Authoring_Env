package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
<<<<<<< HEAD
import oogasalad.model.api.exception.InvalidParameterNumberException;
=======

import java.util.ArrayList;
import oogasalad.model.api.exception.InvalidParameterNumberException;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.command.AddDelayedPointsCommand;
>>>>>>> 6efb8acf8708781d7e628a35311188dd1bcf78cb
import oogasalad.model.gameparser.GameLoaderModel;
import org.junit.jupiter.api.Test;

public class CommandExceptionTest {

  @Test
  public void testMissingParamAdjustPointsCommand() {
    assertThrows(InvalidParameterNumberException.class, () -> {
      AddDelayedPointsCommand cmd = new AddDelayedPointsCommand(new ArrayList<>());
      GameEngine gameEngine = new GameEngine("ba");
      cmd.execute(gameEngine);
      GameLoaderModel loader = new GameLoaderModel("badParamNumberMiniGolf");
      loader.prepareRound(1);
    });
  }
}
