package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public abstract class CheckEndConditionCommand implements Command {

  @Override
  public void execute(GameEngine engine, List<Double> arguments) {
    if (evaluateCondition(engine, arguments)) {
      engine.endGame();
    }
  }

  protected abstract boolean evaluateCondition(GameEngine engine, List<Double> arguments);
}
