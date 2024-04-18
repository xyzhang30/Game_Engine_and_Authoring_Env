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
@JsonPropertyOrder({"collisions", "turn_policy", "round_policy", "win_condition", "advance_turn",
    "advance_round"})
public record Rules(@ChoiceType(singleChoice = false) List<CollisionRule> collisions,
                    @ChoiceType(singleChoice = true) @JsonProperty("turn_policy") String turnPolicy,
                    @ChoiceType(singleChoice = true) @JsonProperty("round_policy") Map<String, List<Double>> roundPolicy,
                    @ChoiceType(singleChoice = true) @JsonProperty("win_condition") Map<String, List<Double>> winCondition,
                    @ChoiceType(singleChoice = false) @JsonProperty("advance_turn") List<Map<String, List<Double>>> advanceTurn,
                    @ChoiceType(singleChoice = false) @JsonProperty("advance_round") List<Map<String, List<Double>>> advanceRound,
                    @ChoiceType(singleChoice = true) @JsonProperty("strike_policy") String strikePolicy
) {

}
