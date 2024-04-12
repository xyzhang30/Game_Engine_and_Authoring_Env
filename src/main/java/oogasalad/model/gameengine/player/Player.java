package oogasalad.model.gameengine.player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import javafx.collections.ObservableMap;
import oogasalad.model.api.PlayerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {

  private static final Logger LOGGER = LogManager.getLogger(Player.class);
  private final int playerId;
  private final List<Integer> myControllable;
  private final Map<String, Double> variables;
  private boolean roundCompleted = false;
  private Stack<ObservableMap<String, Double>> variableStack;

  public Player(int id, List<Integer> controlable) {
    playerId = id;
    myControllable = controlable;
    roundCompleted = false;
    variables = new HashMap<>();
    variables.put("score", 0.0);
  }

  public double getVariable(String variable) {
    return variables.getOrDefault(variable, 0.0);
  }

  public void setVariable(String key, double value) {
    variables.put(key, value);
  }

  protected PlayerRecord getPlayerRecord(boolean active) {

    try {
      return new PlayerRecord(playerId, variables.get("score"), myControllable, active);
    } catch (NullPointerException e) {
      LOGGER.warn("Invalid player");
      return null;
    }
  }

  public int getId() {
    return playerId;
  }


  public boolean isRoundCompleted() {
    return roundCompleted;
  }

  public void setRoundCompleted(boolean isCompleted) {
    roundCompleted = isCompleted;
  }

  protected void setFromRecord(PlayerRecord record) {
    variables.put("score", record.score());
  }
}
