package oogasalad.model.gameengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.PlayerRecord;

public class PlayerContainer {

  private final Map<Integer, Player> myPlayers;

  public PlayerContainer(Map<Integer, Player> players) {
    myPlayers = players;
  }

  public Player getPlayer (int playerId) {
    return myPlayers.get(playerId);
  }

  //need some sort of set active players function in here??

  public List<Player> getActivePlayers() {
    List<Player> activePlayers = new ArrayList<>();
    for (Player player : myPlayers.values()) {
      if (player.isActive()) {
        activePlayers.add(player);
      }
    }
    return activePlayers;
  }

  public List<PlayerRecord> getPlayerRecords() {
    List<PlayerRecord> ret = new ArrayList<>();
    for(Player p : myPlayers.values()) {
      ret.add(new PlayerRecord(p.getId(), p.getVariable("score")));
    }
    return ret;
  }


}
