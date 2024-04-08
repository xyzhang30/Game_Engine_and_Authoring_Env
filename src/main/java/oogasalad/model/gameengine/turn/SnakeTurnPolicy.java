package oogasalad.model.gameengine.turn;

import oogasalad.model.gameengine.PlayerContainer;
import oogasalad.model.gameengine.turn.TurnPolicy;

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
    return turn;
  }
}
