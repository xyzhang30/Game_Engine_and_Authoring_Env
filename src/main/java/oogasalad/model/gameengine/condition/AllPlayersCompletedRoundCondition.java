package oogasalad.model.gameengine.condition;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class AllPlayersCompletedRoundCondition implements Condition {

  public AllPlayersCompletedRoundCondition(List<Double> arguments){

  }

  @Override
  public boolean evaluate(GameEngine engine) {
    return engine.getPlayerContainer().allPlayersCompletedRound();
  }
}
