package oogasalad.model.gameengine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javafx.collections.ObservableMap;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.collidable.Collidable;


public class Player {

  private final int playerId;
  private final Collidable myCollidable;
  private final Map<String, Double> variables;

  private Stack<ObservableMap<String, Double>> variableStack;

  public Player(int id, Collidable collidable) {
    playerId = id;
    myCollidable = collidable;
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
      return new PlayerRecord(playerId, variables.get("score"), myCollidable.getId(), active);
    } catch (NullPointerException e) {
      return null;
    }
  }

  public int getId() {
    return playerId;
  }

  public Map<String, Double> getObservableVariables() {
    return Collections.unmodifiableMap(variables);
  }

  public void setObservableVariables(Map<String, Double> variablesOld) {
    variables.clear();
    for (String key : variablesOld.keySet()) {
      variables.put(key, variablesOld.get(key));
    }

  }

}
