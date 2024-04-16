package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import oogasalad.model.api.exception.InvalidParameterNumberException;
import oogasalad.model.gameparser.GameLoaderModel;
import org.junit.jupiter.api.Test;

public class CommandExceptionTest {

  @Test
  public void testMissingParamAdjustPointsCommand() {
    assertThrows(InvalidParameterNumberException.class, () -> {
      GameLoaderModel loader = new GameLoaderModel("badParamNumberMiniGolf");
      loader.makeLevel(1);
    });
  }
}
