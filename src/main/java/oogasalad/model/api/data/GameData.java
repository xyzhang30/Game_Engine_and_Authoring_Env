package oogasalad.model.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

/**
 * Represents all JSON data configuring the game that gets serialized/deserialized into/from JSON
 */

@JsonPropertyOrder({"gameName", "collidable_objects", "players", "variables", "rules"})
public class GameData {

  private @JsonProperty("gameName") String gameName;
  private @JsonProperty("collidable_objects") List<CollidableObject> collidableObjects;
  private List<ParserPlayer> players;
  private List<Variables> variables;
  private Rules rules;

  public Rules getRules() {
    return rules;
  }

  public void setRules(Rules rules) {
    this.rules = rules;
  }

  public @JsonProperty("gameName") String getGameName() {
    return gameName;
  }

  public void setGameName(@JsonProperty("gameName") String gameName) {
    this.gameName = gameName;
  }

  public @JsonProperty("collidable_objects") List<CollidableObject> getCollidableObjects() {
    return collidableObjects;
  }

  public void setCollidableObjects(
      @JsonProperty("collidable_objects") List<CollidableObject> collidables) {
    this.collidableObjects = collidables;
  }

  public List<ParserPlayer> getPlayers() {
    return players;
  }

  public void setPlayers(List<ParserPlayer> players) {
    this.players = players;
  }

  public List<Variables> getVariables() {
    return variables;
  }

  public void setVariables(List<Variables> variables) {
    this.variables = variables;
  }
}