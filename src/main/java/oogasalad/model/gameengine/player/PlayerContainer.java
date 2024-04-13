package oogasalad.model.gameengine.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import oogasalad.model.api.PlayerRecord;

public class PlayerContainer {

  private final Map<Integer, Player> myPlayers;
  private final Stack<List<PlayerRecord>> playerHistory;
  private int active;

  public PlayerContainer(Map<Integer, Player> players) {
    myPlayers = players;
    playerHistory = new Stack<>();
    addPlayerHistory();

  }

  public int getNumPlayers() {
    return myPlayers.size();
  }

  public Player getPlayer(int playerId) {
    return myPlayers.get(playerId);
  }

  //need some sort of set active players function in here??
  public int getActive() {
    return active;
  }

  public void setActive(int newActive) {
    active = newActive;
  }

  public List<PlayerRecord> getPlayerRecords() {
    List<PlayerRecord> ret = new ArrayList<>();
    for (Player p : myPlayers.values()) {
      ret.add(p.getPlayerRecord(active == p.getId()));
    }
    return ret;
  }

  public void addPlayerHistory() {
    playerHistory.push(getPlayerRecords());
  }


  private void callSetFromRecord(PlayerRecord record) {
    getPlayer(record.playerId()).setFromRecord(record);
  }

  public void toLastStaticStateVariables() {
    for (PlayerRecord record : playerHistory.peek()) {
      callSetFromRecord(record);
    }
  }

  public boolean allPlayersCompletedRound() {
    for (Player p : myPlayers.values()) {

      if (!p.isRoundCompleted()) {
        return false;
      }
    }
    return true;
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    for (Player p : myPlayers.values()) {
      s.append("\n" + p.toString());
    }
    return s.toString();
  }

  public void startRound() {
    for (Player p : myPlayers.values()) {
      p.setRoundCompleted(false);
    }
  }
}
