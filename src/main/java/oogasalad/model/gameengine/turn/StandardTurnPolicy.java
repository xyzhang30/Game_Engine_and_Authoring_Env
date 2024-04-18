package oogasalad.model.gameengine.turn;

import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.player.PlayerContainer;

/**
 * The StandardTurnPolicy class implements the TurnPolicy interface by defining a standard turn
 * policy where players take turns in a sequential order. This turn policy ensures that each player
 * takes a turn in order, skipping players who have completed their rounds until an active player is
 * found. Essentially, a circular list is used.
 */

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class StandardTurnPolicy implements TurnPolicy {

  private final PlayerContainer playerContainer;

  /**
   * Initializes a StandardTurnPolicy object with the specified player container.
   *
   * @param playerContainer The player container containing the players in the game.
   */

  public StandardTurnPolicy(PlayerContainer playerContainer) {
    this.playerContainer = playerContainer;
  }

  /**
   * Retrieves the player's turn based on the standard turn policy, which utilizes a circular list.
   *
   * @return The id of the player whose turn is next.
   */

  @Override
  public int getNextTurn() {
    int numPlayers = playerContainer.getNumPlayers();
    int turn = ((playerContainer.getActive()) % numPlayers) + 1;
    playerContainer.setActive(turn);
    while (playerContainer.getPlayer(turn).isRoundCompleted()) {
      turn = (turn % numPlayers) + 1;
      playerContainer.setActive(turn);
    }
    playerContainer.getPlayer(turn).updateActiveStrikeable();
    return turn;
  }
}
