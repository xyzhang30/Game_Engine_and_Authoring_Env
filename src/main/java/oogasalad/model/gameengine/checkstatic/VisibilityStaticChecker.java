package oogasalad.model.gameengine.checkstatic;

import java.util.List;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.annotations.VariableParamNumber;
import oogasalad.model.api.GameObjectRecord;

@IsCommand(isCommand = true)
public class VisibilityStaticChecker implements StaticChecker {

  private final List<Integer> arguments;

  /**
   * Constructs a VisibilityStaticChecker with a list of GameObjects as parameters.
   *
   * @param arguments The list of game objects that this velocity static checker applies to.
   */

  @VariableParamNumber(isVariable = true)
  public VisibilityStaticChecker(List<Integer> arguments) {
    this.arguments = arguments;
  }

  /**
   * Checks if the given game object record is static based on visibility.
   *
   * @param record The game object record to be checked.
   * @return true if the object is not visible, false otherwise.
   */

  @Override
  public boolean isStatic(GameObjectRecord record) {
    if (arguments.isEmpty() || arguments.contains(record.id())) {
      return !record.visible();
    }
    return true;
  }
}
