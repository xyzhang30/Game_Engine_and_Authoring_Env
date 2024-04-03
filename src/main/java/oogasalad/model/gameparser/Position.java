package oogasalad.model.gameparser;

import com.fasterxml.jackson.annotation.JsonProperty;

record Position(@JsonProperty("x_position") int xPosition, @JsonProperty("y_position") int yPosition) {}

