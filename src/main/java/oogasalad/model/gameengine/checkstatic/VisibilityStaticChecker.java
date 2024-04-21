package oogasalad.model.gameengine.checkstatic;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.annotations.VariableParamNumber;
import oogasalad.model.api.GameObjectRecord;

@IsCommand(isCommand = true)
public class VisibilityStaticChecker implements StaticChecker {

  private final List<Integer> arguments;

  @VariableParamNumber(isVariable = true)
  public VisibilityStaticChecker(List<Integer> arguments) { this.arguments=arguments;
  }

  @Override
  public boolean isStatic(GameObjectRecord record) {
    if (arguments.isEmpty() || arguments.contains(record.id())) {
      return !record.visible();
    }
    return true;
  }
}
