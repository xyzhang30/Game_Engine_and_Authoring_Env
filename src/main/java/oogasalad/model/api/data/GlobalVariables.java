package oogasalad.model.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the JSON data for global game variables.
 *
 * @author Judy He
 */
public record GlobalVariables(@JsonProperty("current_turn") int currentTurn,
                              @JsonProperty("current_round") int currentRound) {

}
