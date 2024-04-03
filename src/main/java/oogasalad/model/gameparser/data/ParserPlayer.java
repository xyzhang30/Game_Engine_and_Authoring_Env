package oogasalad.model.gameparser.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the JSON data for a player in the game.
 *
 * @author Judy He
 */
record ParserPlayer(@JsonProperty("player_id") int playerId, @JsonProperty("my_collidable") int myCollidable) {}