package oogasalad.model.gameengine.turn;

import oogasalad.model.annotations.IsCommand;

/**
 * The TurnPolicy interface defines a contract for classes implementing different turn policies
 * within a game engine.
 * <p>
 * Each implementation must provide details of how to execute the turn policy, and determine whose
 * turn is next, following the completion of a turn.
 *
 * @author Noah Loewy
 */

@FunctionalInterface
@IsCommand(isCommand = false)
public interface TurnPolicy {

  /**
   * Retrieves the id of the player whose turn is next based on the specific turn policy.
   *
   * @return The id of the player who is next to go.
   */

  int getNextTurn();
}

