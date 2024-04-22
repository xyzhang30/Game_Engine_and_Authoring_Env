package oogasalad.model.gameengine.condition;

import java.util.List;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;

/**
 * The NTurnsCompletedCondition class represents a condition that evaluates whether a specified
 * number of rounds have been completed in the game by ALL the players.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class NTurnsCompletedCondition implements Condition {

  private final List<Double> arguments;

  /**
   * Constructs an instance of the NTurnsCompletedCondition with the provided arguments.
   *
   * @param arguments A one-element list which represents the number of turns that need to be
   *                  completed by each player for the condition to evaluate to true.
   */

  @ExpectedParamNumber(1)
  public NTurnsCompletedCondition(List<Double> arguments) {
    this.arguments = arguments;
  }

  /**
   * Evaluates whether the specified number of turns have been completed in the game by each player.
   * The work is delegated to PlayerContainer's allPlayersCompletedNTurns method.
   *
   * @param engine The game engine instance.
   * @return true if the game engine's current round exceeds the specified number of rounds to be
   * completed, false otherwise.
   */

  @Override
  public boolean evaluate(GameEngine engine) {
    return engine.getPlayerContainer().getPlayers().stream().allMatch(p -> p.getTurnsCompleted() >= (int) Math.round(arguments.get(0)));
  }
}
