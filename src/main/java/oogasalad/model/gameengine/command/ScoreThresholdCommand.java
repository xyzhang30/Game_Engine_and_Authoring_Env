package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;

public class ScoreThresholdCommand extends CheckEndConditionCommand {

  public ScoreThresholdCommand(List<Double> arguments) {
    super(arguments);
  }

  @Override
  protected boolean evaluateCondition(GameEngine engine, List<Double> arguments) {
    double scoreThresh = arguments.get(0);
    List<PlayerRecord> lst = engine.getImmutablePlayers();
    for(PlayerRecord player : lst) {
      if(player.score() > scoreThresh) {
        return true;
      }
    }
    return false;
  }

}
