package oogasalad.model.gameengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.Player;

public class PlayerContainer {

  private final Map<Integer, Player> myPlayers;
  private int active;

  public PlayerContainer(Map<Integer, Player> players) {
    myPlayers = players;
    active = 1;
  }
  public int getNumPlayers() {
    return myPlayers.size();
  }

  public Player getPlayer (int playerId) {
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
    for(Player p : myPlayers.values()) {
      ret.add(p.getPlayerRecord(active==p.getId()));
    }
    return ret;
  }


}
