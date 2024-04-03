package oogasalad.model.gameparser;

import com.fasterxml.jackson.annotation.JsonProperty;

record GlobalVariables(@JsonProperty("max_turns") int maxTurns, @JsonProperty("max_rounds")int maxRounds) {}
