package oogasalad.model.gameengine.logic;

import java.util.HashMap;
import java.util.Map;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.collidable.Collidable;

public class Player {

  private final int playerId;
  private final Collidable myCollidable;
  private final Map<String, Double> variables;
  private int turns;
  private boolean active;

  public Player(int id, Collidable collidable) {
    playerId = id;
    myCollidable = collidable;
    variables = new HashMap<>();
    variables.put("score", 0.0);
    turns = 1;
  }

  public Collidable getPrimary() {
    return myCollidable;
  }

  public double getVariable(String variable) {
    return variables.getOrDefault(variable,0.0);
  }

  public void setVariable(String key, double value) {
    variables.put(key, value);
  }

  protected boolean isActive() {
    return active;
  }

  protected void setActive(boolean state) {
    active = state;
  }

  protected int getTurnsInRound() {
    return turns;
  }

  protected void setTurnsInRound(int turns) {
    this.turns = turns;
  }

  protected PlayerRecord getPlayerRecord() {
    return new PlayerRecord(playerId, variables.get("score"), myCollidable.getId(), active);
  }

  public int getId() {
    return playerId;
  }
}
