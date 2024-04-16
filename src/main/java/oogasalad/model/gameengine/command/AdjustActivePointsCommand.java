package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.gameengine.GameEngine;

public class AdjustActivePointsCommand extends AdjustPointsGivenPlayerCommand {

  @ExpectedParamNumber(1)
  public AdjustActivePointsCommand(List<Double> arguments) {
    super(arguments);
  }

  @Override
  public void execute(GameEngine engine) {
    adjust(engine, engine.getPlayerContainer().getActive());
  }
}

