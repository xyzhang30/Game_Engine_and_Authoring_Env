package oogasalad.model.gameengine.condition;

import java.util.List;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;



@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class GameObjectsNotVisibleCondition implements Condition {

  private final List<Double> arguments;
  @ExpectedParamNumber(1)
  public GameObjectsNotVisibleCondition(List<Double> arguments) {
    this.arguments = arguments;
  }


  @Override
  public boolean evaluate(GameEngine engine) {
    for(double arg : arguments) {
      int intArg = (int) Math.round(arg);
      if(engine.getGameObjectContainer().getGameObject(intArg).getVisible()) {
        return false;
      }
    }
    return true;
  }

}
