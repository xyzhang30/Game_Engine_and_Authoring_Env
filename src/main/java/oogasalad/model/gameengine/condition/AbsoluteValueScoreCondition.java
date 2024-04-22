package oogasalad.model.gameengine.condition;

import java.util.List;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;


@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class AbsoluteValueScoreCondition implements Condition {

  private final List<Double> arguments;

  @ExpectedParamNumber(1)
  public AbsoluteValueScoreCondition(List<Double> arguments) {
    this.arguments = arguments;
  }


  @Override
  public boolean evaluate(GameEngine engine) {
    double scoreThresh = arguments.get(0);
    List<PlayerRecord> lst = engine.getPlayerContainer().getPlayerRecords();
    for (PlayerRecord player : lst) {
      if (player.score() == Math.abs(scoreThresh)) {
        return true;
      }
    }
    return false;
  }

}
