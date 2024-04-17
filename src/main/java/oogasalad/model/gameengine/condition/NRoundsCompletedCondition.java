package oogasalad.model.gameengine.condition;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.gameengine.GameEngine;

/**
 * The NRoundsCompletedCondition class represents a condition that evaluates whether a specified
 * number of rounds have been completed in the game.
 *
 * @author Noah Loewy
 */

public class NRoundsCompletedCondition implements Condition {

  private final List<Double> arguments;

  /**
   * Constructs an instance of the NRoundsCompletedCondition with the provided arguments.
   *
   * @param arguments A one-element list which represents the number of rounds that need to be
   *                  completed for the condition to evaluate to true.
   */

  @ExpectedParamNumber(1)
  public NRoundsCompletedCondition(List<Double> arguments) {
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
