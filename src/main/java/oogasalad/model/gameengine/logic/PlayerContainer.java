package oogasalad.model.gameengine.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.logic.Player;

public class PlayerContainer {

  private final Map<Integer, Player> myPlayers;

  public PlayerContainer(Map<Integer, Player> players) {
    myPlayers = players;
  }

  public Player getPlayer (int playerId) {
    return myPlayers.get(playerId);
  }

  //need some sort of set active players function in here??

  public List<PlayerRecord> getPlayerRecords() {
    List<PlayerRecord> ret = new ArrayList<>();
    for(Player p : myPlayers.values()) {
      ret.add(p.getPlayerRecord());
    }
    return ret;
  }


}
