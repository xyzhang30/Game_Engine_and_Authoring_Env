package oogasalad.model.gameengine;

import java.util.HashMap;
import java.util.Map;

public class Player {
  private int playerId;
  private Collidable myCollidable;
  private Map<String, Double> variables;

  private boolean active;

  public Player(int id, Collidable collidable) {
    playerId = id;
    myCollidable = collidable;
    variables = new HashMap<>();
    variables.put("score", 0.0);
  }

  public Collidable getPrimary() {
    return myCollidable;
  }

  public double getVariable() {
    return variables.getOrDefault("score",0.0);
  }

  public void setVariable(String key, double value) {
    variables.put(key, value);
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean state) {
    active = state;
  }
}
