package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.api.exception.InvalidParameterNumberException;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.command.AdjustPointsCommand;
import org.junit.jupiter.api.Test;

public class CommandExceptionTest {

  @Test
  public void testMissingParamAdjustPointsCommand() {
    assertThrows(InvalidParameterNumberException.class, () -> {
      AdjustPointsCommand cmd = new AdjustPointsCommand(new ArrayList<>());
      GameEngine gameEngine = new GameEngine("chapel");
      cmd.execute(gameEngine);
    });
  }
}
