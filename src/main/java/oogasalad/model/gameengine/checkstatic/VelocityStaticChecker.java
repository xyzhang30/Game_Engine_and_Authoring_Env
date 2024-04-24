package oogasalad.model.gameengine.checkstatic;

import java.util.List;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.annotations.VariableParamNumber;
import oogasalad.model.api.GameObjectRecord;

/**
 * Represents a velocity-based static checker for game objects.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
@VariableParamNumber(isVariable = true)
public class VelocityStaticChecker implements StaticChecker {

  private final List<Integer> arguments;

  /**
   * Only the game objects with ids in arguments will be checked. If arguments is empty, then all
   * ids will be checked
   */
  public VelocityStaticChecker(List<Integer> arguments) {
    this.arguments = arguments;
  }

  /**
   * Checks if the given game object record is static based on velocity.
   *
   * @param record The game object record to be checked.
   * @return true if the object has negligible velocity or is not visible, false otherwise.
   */

  @Override
  public boolean isStatic(GameObjectRecord record) {
    if (arguments.isEmpty() || arguments.contains(record.id())) {
      return !record.visible() || (Math.abs(record.velocityX()) < 1
          && Math.abs(record.velocityY()) < 1);
    }
    return true;
  }
}
