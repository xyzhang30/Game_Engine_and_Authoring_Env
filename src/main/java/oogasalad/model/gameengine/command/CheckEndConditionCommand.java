package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public abstract class CheckEndConditionCommand implements Command {

  @Override
  public double execute(GameEngine engine, List<Double> arguments) {
    if(evaluateCondition(engine, arguments)) {
      engine.endGame();
      return 1.0;
    }
    return 0.0;
  }

  protected abstract boolean evaluateCondition(GameEngine engine, List<Double> arguments);
}
