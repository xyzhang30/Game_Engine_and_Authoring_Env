package oogasalad.model.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the JSON data for the dimension of a collidable object.
 *
 * @author Judy He
 */
public record Dimension(@JsonProperty("x_dimension") double xDimension,
                        @JsonProperty("y_dimension") double yDimension) {

}

