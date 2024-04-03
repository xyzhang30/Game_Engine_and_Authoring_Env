package oogasalad.model.gameparser;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

record Rules(List<CollisionRule> collisions, @JsonProperty("static_checks") List<StaticCheckRule> staticChecks) {}

