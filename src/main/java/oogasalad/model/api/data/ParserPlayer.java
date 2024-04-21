package oogasalad.model.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Optional;

/**
 * Represents the JSON data for a player in the game.
 *
 * @author Judy He
 */
public record ParserPlayer(@JsonProperty("player_id") int playerId,
                           @JsonProperty("my_strikeable") List<Integer> myStrikeable,
                           @JsonProperty("my_controllable") List<Integer> myControllable) {

}