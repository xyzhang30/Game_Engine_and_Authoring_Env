package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class AdjustActivePointsCommand extends AdjustPointsGivenPlayerCommand {

  public AdjustActivePointsCommand(List<Double> arguments) {
    super(arguments);
  }

  @Override
  public double execute(GameEngine engine) {
    return adjust(engine, engine.getPlayerContainer().getActive());
  }
}

