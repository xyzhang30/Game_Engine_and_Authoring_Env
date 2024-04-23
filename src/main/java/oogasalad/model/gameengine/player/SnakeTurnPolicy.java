package oogasalad.model.gameengine.player;

import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.IsCommand;

/**
 * The SnakeTurnPolicy class implements the TurnPolicy interface by defining a turn policy where
 * players take turns in a "snake-like" order (with inspiration from a Fantasy Football snake
 * draft). The policy iterates through the player list in order, alternating whether it iterates
 * forward or backwards with each iteration, and skipping players who have completed their rounds
 * until a player who has yet to complete it is found.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class SnakeTurnPolicy implements TurnPolicy {

  private final PlayerContainer playerContainer;
  private final boolean forward;

  /**
   * Initializes a SnakeTurnPolicy object with the specified player container.
   *
   * @param playerContainer The player container containing the players in the game.
   */

  public SnakeTurnPolicy(PlayerContainer playerContainer) {
    this.playerContainer = playerContainer;
    forward = true;

  }

  /**
   * Retrieves the player's turn based on the snake turn policy, which iterates through the player
   * list, alternating whether it iterates forward or backwards with each iteration, and skipping
   * players who have already completed the round.
   *
   * @return The id of the player whose turn is next.
   */

  @Override
  public int getNextTurn() {
    throw new RuntimeException("Not Implemented Yet");
  }
}
