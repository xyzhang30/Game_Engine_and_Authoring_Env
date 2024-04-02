package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class AdvanceTurnCommand implements Command {

  private List<Double> arguments;

  public AdvanceTurnCommand(List<Double> arguments) {
    this.arguments = arguments;
  }
  @Override
  public double execute(GameEngine engine) {
    engine.advanceTurn();
    return 0.0;
  }

}
