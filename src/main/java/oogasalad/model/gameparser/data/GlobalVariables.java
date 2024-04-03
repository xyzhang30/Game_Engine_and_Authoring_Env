package oogasalad.model.gameparser.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the JSON data for global game variables.
 *
 * @author Judy He
 */
public record GlobalVariables(@JsonProperty("max_turns") int maxTurns, @JsonProperty("max_rounds")int maxRounds) {}
