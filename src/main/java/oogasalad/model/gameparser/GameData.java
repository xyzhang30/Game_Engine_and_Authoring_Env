package oogasalad.model.gameparser;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

record GameData(@JsonProperty("gameName") String gameName,
                @JsonProperty("collidable_objects") List<CollidableObject> collidableObjects,
                List<ParserPlayer> players,
                List<Variables> variables,
                Rules rules) {}