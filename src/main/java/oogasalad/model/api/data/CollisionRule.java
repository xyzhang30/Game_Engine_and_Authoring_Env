package oogasalad.model.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import java.util.Map;

/**
 * Represents the JSON data for a collision rule in the game.
 *
 * @author Judy He
 */
@JsonPropertyOrder({"first_id", "second_id", "command"})
public record CollisionRule(@JsonProperty("first_id") int firstId,
                            @JsonProperty("second_id") int secondId,
                            List<Map<String, List<Double>>> command) {

}