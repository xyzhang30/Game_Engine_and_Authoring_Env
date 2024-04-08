package oogasalad.model.gameengine.turn;

import oogasalad.model.gameengine.PlayerContainer;
import oogasalad.model.gameengine.turn.TurnPolicy;

public class StandardTurnPolicy implements TurnPolicy {

  private final PlayerContainer playerContainer;

  public StandardTurnPolicy(PlayerContainer playerContainer) {
    this.playerContainer = playerContainer;
  }

  @Override
  public int getTurn() {
    int numPlayers = playerContainer.getNumPlayers();
    int turn = (playerContainer.getActive() + 1) % numPlayers + numPlayers;
    playerContainer.setActive(turn);
    return turn;
  }
}
