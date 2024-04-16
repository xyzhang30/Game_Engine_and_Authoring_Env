package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.gameengine.GameEngine;

public class StopCommand implements Command {

  private final List<Double> arguments;

  @ExpectedParamNumber(0)
  public StopCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public void execute(GameEngine engine) {
    engine.getCollidableContainer().makeStatic();
  }

}