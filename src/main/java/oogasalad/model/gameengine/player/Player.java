package oogasalad.model.gameengine.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.PlayerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {

  private static final Logger LOGGER = LogManager.getLogger(Player.class);
  private final int playerId;
  private final List<Integer> myControllables;
  private final Map<String, Double> variables;
  private int activeControllable;
  private boolean roundCompleted = false;

  public Player(int id, List<Integer> controlable) {
    playerId = id;
    myControllables = controlable;
    System.out.println(myControllables);
    roundCompleted = false;
    activeControllable = myControllables.get(0);
    variables = new HashMap<>();
    variables.put("score", 0.0);
  }

  public double getVariable(String variable) {
    return variables.getOrDefault(variable, 0.0);
  }

  //TODO
  //later make this an abstraction
  public void updateActiveControllableId() {
    activeControllable =
        myControllables.get(
            (myControllables.indexOf(activeControllable) + 1) % myControllables.size());
  }

  public void setVariable(String key, double value) {
    variables.put(key, value);
  }

  protected PlayerRecord getPlayerRecord(boolean active) {
    try {
      return new PlayerRecord(playerId, variables.get("score"), activeControllable, active);
    } catch (NullPointerException e) {
      LOGGER.warn("Invalid player");
      return null;
    }
  }

  public int getId() {
    return playerId;
  }


  //public String toString() {
  //   return "ID " + playerId + "\n\tRoundCompleted " + roundCompleted + "\n\t" + "Score "
  //    + variables.get("score")+"\n\t";
  //}
  public boolean isRoundCompleted() {
    return roundCompleted;
  }

  public void setRoundCompleted(boolean isCompleted) {
    roundCompleted = isCompleted;
  }

  public int getControllableId() {
    return myControllables.get(myControllables.indexOf(activeControllable));
  }

  protected void setFromRecord(PlayerRecord record) {
    variables.put("score", record.score());
  }

}
