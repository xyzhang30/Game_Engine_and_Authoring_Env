package oogasalad.model.gameengine.condition;

import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;

/**
 * The NRoundsCompletedCondition class represents a condition that evaluates whether a specified
 * number of rounds have been completed in the game.
 *
 * @author Noah Loewy
 */


@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
@ExpectedParamNumber(1)
public class NRoundsCompletedCondition implements Condition {

  private final List<Integer> arguments;

  /**
   * Constructs an instance of the NRoundsCompletedCondition with the provided arguments.
   *
   * @param arguments A one-element list which represents the number of rounds that need to be
   *                  completed for the condition to evaluate to true.
   */

  public NRoundsCompletedCondition(List<Integer> arguments,
      Map<Integer, GameObject> gameObjectMap) {
    this.arguments = arguments;
  }

  /**
   * Evaluates whether the specified number of rounds have been completed in the game.
   *
   * @param engine The game engine instance.
   * @return true if the game engine's current round exceeds the specified number of rounds to be
   * completed, false otherwise.
   */

  @Override
  public boolean evaluate(GameEngine engine) {
    return engine.restoreLastStaticGameRecord().round() > arguments.get(0);
  }
}
