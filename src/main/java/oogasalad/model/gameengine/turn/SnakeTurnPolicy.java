package oogasalad.model.gameengine.turn;

import oogasalad.model.gameengine.player.PlayerContainer;

public class SnakeTurnPolicy implements TurnPolicy {

  private final PlayerContainer playerContainer;
  private final boolean forward;

  public SnakeTurnPolicy(PlayerContainer playerContainer) {
    this.playerContainer = playerContainer;
    forward = true;

  }

  @Override
  public int getTurn() {
    int numPlayers = playerContainer.getNumPlayers();
    int turn;
    if (forward) {
      turn = (playerContainer.getActive() + 1) % numPlayers + numPlayers;
    } else {
      turn = (playerContainer.getActive() - 1 + numPlayers) % numPlayers;
    }
    playerContainer.getPlayer(turn).updateActiveControllableId();
    return turn;
  }
}
