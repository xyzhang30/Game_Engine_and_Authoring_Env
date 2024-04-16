package oogasalad.model.gameengine.condition;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.gameengine.GameEngine;

public class NRoundsCompletedCondition implements Condition {

  private final List<Double> arguments;

  @ExpectedParamNumber(1)
  public NRoundsCompletedCondition(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public boolean evaluate(GameEngine engine) {
    return engine.getRound() > arguments.get(0);
  }

}
