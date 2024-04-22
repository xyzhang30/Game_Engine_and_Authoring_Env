package oogasalad.model.gameengine.condition;

import java.util.List;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;


@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class NoneVisibleCondition implements Condition {

  private final List<Double> arguments;

  @ExpectedParamNumber(1)
  public NoneVisibleCondition(List<Double> arguments) {
    this.arguments = arguments;
  }


  @Override
  public boolean evaluate(GameEngine engine) {
    if(engine.getGameObjectContainer().getGameObject((int) Math.round(arguments.get(0))).getVisible()) {
      return false;
    }
    return true;
  }

}
