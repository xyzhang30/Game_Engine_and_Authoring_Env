package oogasalad.model.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.ChoiceType;

/**
 * Represents the JSON data for all rules (collision, static check) in the game.
 *
 * @author Judy He, Alisha Zhang
 */

public record Rules(List<CollisionRule> collisions,
                    @JsonProperty("turn_policy") String turnPolicy,
                    @JsonProperty("round_policy") Map<String, List<Double>> roundPolicy,
                    @JsonProperty("win_condition") Map<String, List<Double>> winCondition,
                    @JsonProperty("advance_turn") Map<String, List<Double>> advanceTurn,
                    @JsonProperty("advance_round") Map<String, List<Double>> advanceRound,
                    @JsonProperty("strike_policy") String strikePolicy,
                    @JsonProperty("rank_comparator") String rankComparator,
                    @JsonProperty("static_checker_type") String staticCheckerType,
                    @JsonProperty("static_checker_params") List<Integer> staticCheckerParams){




}
