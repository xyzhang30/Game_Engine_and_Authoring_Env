package oogasalad.model.gameengine.checkstatic;

import java.util.List;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.annotations.VariableParamNumber;
import oogasalad.model.api.GameObjectRecord;

@IsCommand(isCommand = true)
@VariableParamNumber(isVariable = true)
public class VelocityStaticChecker implements StaticChecker {

  private final List<Integer> arguments;

  public VelocityStaticChecker(List<Integer> arguments) {
    this.arguments = arguments;
  }

  @Override
  public boolean isStatic(GameObjectRecord record) {
    if (arguments.isEmpty() || arguments.contains(record.id())) {
      return !record.visible() || (Math.abs(record.velocityX()) < 1
          && Math.abs(record.velocityY()) < 1);
    }
    return true;
  }
}
