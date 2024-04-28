package oogasalad.model.gameengine.checkstatic;

import oogasalad.model.annotations.IsCommand;
import oogasalad.model.api.GameObjectRecord;

/**
 * Represents a functional interface for checking if a game object is static.
 *
 * @author Noah Loewy
 */

@FunctionalInterface
@IsCommand(isCommand = false)
public interface StaticChecker {

  /**
   * Checks if the given game object record is static.
   *
   * @param record The game object record to be checked.
   * @return true if the object is static, false otherwise.
   */

  boolean isStatic(GameObjectRecord record);

}
