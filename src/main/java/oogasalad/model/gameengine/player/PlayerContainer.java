package oogasalad.model.gameengine.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.PlayerRecord;

public class PlayerContainer {

  private final Map<Integer, Player> myPlayers;
  private int active;

  public PlayerContainer(Map<Integer, Player> players) {
    myPlayers = players;
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
      ret.add(p.getPlayerRecord());
    }
    return ret;
  }

  public void addPlayerHistory() {
    for(Player p : myPlayers.values()) {
      p.addPlayerHistory();
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
      p.startRound();
    }
  }

  public void applyDelayedScores() {
    for (Player p : myPlayers.values()) {
      p.applyDelayedScore();
    }

  }

  public boolean allPlayersCompletedNTurns(int turnsRequired) {
    for (Player p : myPlayers.values()) {
      if (!(p.getTurnsCompleted() >= turnsRequired)) {
        return false;
      }
    }
    return true;

  }

  public void toLastStaticStatePlayers() {
    for(Player p : myPlayers.values()) {
      p.toLastStaticStatePlayers();
    }
  }
}
