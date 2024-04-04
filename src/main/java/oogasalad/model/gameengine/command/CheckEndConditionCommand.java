package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public abstract class CheckEndConditionCommand implements Command {

  private final List<Double> arguments;

  public CheckEndConditionCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public double execute(GameEngine engine) {
    if (evaluateCondition(engine, arguments)) {
      engine.endGame();
      return 1.0;
    }
    return 0.0;
  }

  protected abstract boolean evaluateCondition(GameEngine engine, List<Double> arguments);
}
