package oogasalad.model.gameparser;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

record CollisionRule(@JsonProperty("first_id") int firstId,
                     @JsonProperty("second_id") int secondId,
                     List<Map<String, List<Integer>>> command) {}