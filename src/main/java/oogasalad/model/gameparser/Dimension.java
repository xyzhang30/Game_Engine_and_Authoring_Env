package oogasalad.model.gameparser;

import com.fasterxml.jackson.annotation.JsonProperty;

record Dimension(@JsonProperty("x_dimension") int xDimension, @JsonProperty("y_dimension") int yDimension) {}

