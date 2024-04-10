package oogasalad.model.gameengine.condition;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class NRoundsCompletedCondition implements Condition {

  private final List<Double> arguments;

  public NRoundsCompletedCondition(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public boolean evaluate(GameEngine engine) {
    return engine.getRound() > arguments.get(0);
  }

}
