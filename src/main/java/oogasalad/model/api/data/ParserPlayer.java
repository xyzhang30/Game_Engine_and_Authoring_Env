package oogasalad.model.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the JSON data for a player in the game.
 *
 * @author Judy He
 */
public record ParserPlayer(@JsonProperty("player_id") int playerId,
                           @JsonProperty("my_collidable") int myCollidable) {

}