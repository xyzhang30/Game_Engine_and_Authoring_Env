package oogasalad.model.gameparser.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents the JSON data for all rules (collision, static check) in the game.
 *
 * @author Judy He
 */
public record Rules(List<CollisionRule> collisions, @JsonProperty("static_checks") List<StaticCheckRule> staticChecks) {}

