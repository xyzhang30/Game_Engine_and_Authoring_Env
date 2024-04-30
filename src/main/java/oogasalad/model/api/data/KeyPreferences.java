package oogasalad.model.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KeyPreferences(@JsonProperty("angle_left") String angle_left,
                             @JsonProperty("angle_right") String angle_right,
                             @JsonProperty("power_up") String power_up,
                             @JsonProperty("power_down") String power_down,
                             @JsonProperty("controllable_left") String controllable_left,
                             @JsonProperty("controllable_right") String controllable_right,
                             String controllable_up,
                             String striking
) {

}
