package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class AdvanceRoundCommand implements Command {

  private final List<Double> arguments;

  @ExpectedParamNumber(0)
  public AdvanceRoundCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public void execute(GameEngine engine) {
    engine.advanceRound();
  }
}
