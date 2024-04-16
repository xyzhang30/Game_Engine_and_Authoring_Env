package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.gameengine.GameEngine;

public class MultiplySpeedCommand implements Command {

  private final List<Double> arguments;

  @ExpectedParamNumber(2)
  public MultiplySpeedCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public void execute(GameEngine engine) {
    engine.getCollidableContainer().getCollidable((int) Math.round(arguments.get(0)))
        .multiplySpeed(arguments.get(1));
  }
}
