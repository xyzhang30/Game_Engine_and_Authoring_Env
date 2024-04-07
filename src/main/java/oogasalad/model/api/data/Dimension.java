package oogasalad.model.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the JSON data for the dimension of a collidable object.
 *
 * @author Judy He
 */
public record Dimension(@JsonProperty("x_dimension") int xDimension,
                        @JsonProperty("y_dimension") int yDimension) {

}

