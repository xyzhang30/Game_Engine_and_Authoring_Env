package oogasalad.model.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the JSON data for the position of a collidable object.
 *
 * @author Judy He
 */
public record Position(@JsonProperty("x_position") int xPosition,
                       @JsonProperty("y_position") int yPosition) {

}

