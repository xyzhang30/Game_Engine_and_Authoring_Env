package oogasalad.model.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

/**
 * Represents the JSON data for a collidable object in the game.
 *
 * @author Judy He
 */
@JsonPropertyOrder({"gameobject_id", "properties", "mass", "position", "shape", "dimension",
    "color", "staticFriction", "kineticFriction", "inclineAngle", "image"})
public record GameObjectProperties(@JsonProperty("gameobject_id") int collidableId,
                                   List<String> properties,
                                   double mass,
                                   Position position,
                                   String shape,
                                   Dimension dimension,
                                   List<Integer> color,
                                   double staticFriction,
                                   double kineticFriction,
                                   double inclineAngle,
                                   String image,
                                   double direction
) {

}