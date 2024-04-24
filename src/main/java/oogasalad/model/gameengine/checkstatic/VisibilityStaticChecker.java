package oogasalad.model.gameengine.checkstatic;

import java.util.List;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.annotations.VariableParamNumber;
import oogasalad.model.api.GameObjectRecord;

/**
 * Represents a visibility-based static checker for game objects.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
@VariableParamNumber(isVariable = true)
public class VisibilityStaticChecker implements StaticChecker {

  private final List<Integer> arguments;

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
