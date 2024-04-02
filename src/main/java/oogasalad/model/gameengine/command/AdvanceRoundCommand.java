package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class AdvanceRoundCommand implements Command {

  private List<Double> arguments;

  public AdvanceRoundCommand(List<Double> arguments) {
    this.arguments = arguments;
  }
  @Override
  public double execute(GameEngine engine) {
    engine.advanceRound();
    return 0.0;
  }
}
