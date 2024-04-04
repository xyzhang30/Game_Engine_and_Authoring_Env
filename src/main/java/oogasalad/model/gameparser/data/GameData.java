package oogasalad.model.gameparser.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents all JSON data configuring the game
 *
 * @author Judy He
 */
public record GameData(@JsonProperty("gameName") String gameName,
                       @JsonProperty("collidable_objects") List<CollidableObject> collidableObjects,
                       List<ParserPlayer> players,
                       List<Variables> variables,
                       Rules rules) {

}