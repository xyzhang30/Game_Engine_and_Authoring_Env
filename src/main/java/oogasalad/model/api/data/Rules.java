package oogasalad.model.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * Represents the JSON data for all rules (collision, static check) in the game.
 *
 * @author Judy He, Alisha Zhang
 */

public record Rules(List<CollisionRule> collisions,
                    @JsonProperty("turn_policy") String turnPolicy,
                    @JsonProperty("round_policy") Map<String, List<Integer>> roundPolicy,
                    @JsonProperty("win_condition") Map<String, List<Integer>> winCondition,
                    @JsonProperty("advance_turn") Map<String, List<Integer>> advanceTurn,
                    @JsonProperty("advance_round") Map<String, List<Integer>> advanceRound,
                    @JsonProperty("strike_policy") String strikePolicy,
                    @JsonProperty("rank_comparator") String rankComparator,
                    @JsonProperty("static_checker") Map<String, List<Integer>> staticChecker) {

}
