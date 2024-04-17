package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.gameengine.GameEngine;

public class AdvanceRoundCommand implements Command {


  @ExpectedParamNumber(0)
  public AdvanceRoundCommand(List<Double> arguments) {

  }

  @Override
  public void execute(GameEngine engine) {
    engine.advanceRound();
  }
}
