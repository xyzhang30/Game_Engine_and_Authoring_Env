package oogasalad.model.gameengine.strike;

import java.util.function.BiConsumer;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;

/**
 * The DoNothingStrikePolicy class implements the StrikePolicy interface by defining a no-operation
 * (no-op) strike policy. This occurs for cases such as pushing a shuffleboard struck, where the act
 * of applying the force to the puck does not result in any change in points, or any additional
 * change being applied to the strikeable nor potential scoreable. This follows the Null Object
 * Pattern.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
public class DoNothingStrikePolicy implements StrikePolicy {

  public DoNothingStrikePolicy(){}

  /**
   * @return a BiConsumer that does nothing
   */
  @Override
  public BiConsumer<Integer, GameEngine> getStrikePolicy() {
    return (strikeableID, engine) -> {
    }; // Do nothing
  }
}

