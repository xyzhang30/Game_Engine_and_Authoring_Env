package oogasalad.model.gameengine.condition;

import java.util.List;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;

public class ScoreThresholdCondition implements Condition {

  private final List<Double> arguments;
  public ScoreThresholdCondition(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public boolean evaluate(GameEngine engine) {
    double scoreThresh = arguments.get(0);
    List<PlayerRecord> lst = engine.getImmutablePlayers();
    for (PlayerRecord player : lst) {
      if (player.score() > scoreThresh) {
        return true;
      }
    }
    return false;
  }

}
