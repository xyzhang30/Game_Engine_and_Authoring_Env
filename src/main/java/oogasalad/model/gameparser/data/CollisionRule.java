package oogasalad.model.gameparser.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * Represents the JSON data for a collision rule in the game.
 *
 * @author Judy He
 */
public record CollisionRule(@JsonProperty("first_id") int firstId,
                     @JsonProperty("second_id") int secondId,
                     List<Map<String, List<Integer>>> command) {}