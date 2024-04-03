package oogasalad.model.gameengine;

public class StandardTurnPolicy implements TurnPolicy {
private PlayerContainer playerContainer;

public StandardTurnPolicy(PlayerContainer playerContainer) {
    this.playerContainer = playerContainer;
  }
  @Override
  public int getTurn() {
  int numPlayers = playerContainer.getNumPlayers();
  System.out.println(playerContainer.getActive());
  int turn = (playerContainer.getActive() + 1) % numPlayers + numPlayers;
  System.out.println(turn);
  playerContainer.setActive(turn);
  return turn;
}
}
