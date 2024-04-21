package oogasalad.model.gameengine.checkstatic;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.annotations.VariableParamNumber;
import oogasalad.model.api.GameObjectRecord;

@IsCommand(isCommand = true)
public class VelocityStaticChecker implements StaticChecker {
  private final List<Integer> arguments;

  @VariableParamNumber(isVariable = true)
  public VelocityStaticChecker(List<Integer> arguments) { this.arguments=arguments;
  }

  @Override
  public boolean isStatic(GameObjectRecord record) {
    if (arguments.isEmpty() || arguments.contains(record.id())) {
      return !record.visible() || ((record.velocityX() == 0 && record.velocityY() == 0));
    }
    return true;
  }
}
