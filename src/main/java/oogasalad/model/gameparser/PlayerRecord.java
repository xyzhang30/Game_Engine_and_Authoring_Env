package oogasalad.model.gameparser;

import com.fasterxml.jackson.annotation.JsonProperty;

record PlayerRecord(@JsonProperty("player_id") int playerId, @JsonProperty("my_collidable") int myCollidable) {}