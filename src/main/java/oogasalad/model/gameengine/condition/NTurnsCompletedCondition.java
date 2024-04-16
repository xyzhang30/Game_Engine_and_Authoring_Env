package oogasalad.model.gameengine.condition;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class NTurnsCompletedCondition implements Condition {

  private final List<Double> arguments;

  public NTurnsCompletedCondition(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public boolean evaluate(GameEngine engine) {
    return engine.getPlayerContainer().allPlayersCompletedNTurns((int) Math.round(arguments.get(0)));
  }

}
