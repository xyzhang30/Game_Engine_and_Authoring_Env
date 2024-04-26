package oogasalad.model.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KeyPreferences(@JsonProperty("angle_left") String angleLeft,
                             @JsonProperty("angle_right") String angleRight,
                             @JsonProperty("power_up") String powerUp,
                             @JsonProperty("power_down") String powerDown,
                             @JsonProperty("controllable_left") String controllableLeft,
                             @JsonProperty("controllable_right") String controllableRight,
                             @JsonProperty("controllable_up") String controllableUp,
                             @JsonProperty("controllable_down") String controllableDown
                             ) {
}
