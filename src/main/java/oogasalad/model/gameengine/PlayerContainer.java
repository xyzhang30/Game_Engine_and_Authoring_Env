package oogasalad.model.gameengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import oogasalad.model.api.PlayerRecord;

public class PlayerContainer {

  private final Map<Integer, Player> myPlayers;
  private int active;

  private final Stack<Map<Integer, Map<String, Double>>> staticStateVariables;

  public PlayerContainer(Map<Integer, Player> players) {
    myPlayers = players;
    active = 1;
    staticStateVariables = new Stack<>();
    addStaticStateVariables();

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

  public void addStaticStateVariables() {
    staticStateVariables.push(new HashMap<>());
    for (Player p : myPlayers.values()) {
      staticStateVariables.peek().put(p.getId(), p.getObservableVariables());
    }
    staticStateVariables.push(Collections.unmodifiableMap(staticStateVariables.pop()));
  }

  public void toLastStaticStateVariables() {
    for (Integer id : staticStateVariables.peek().keySet()) {
      getPlayer(id).setObservableVariables(staticStateVariables.peek().get(id));
    }
  }
}
