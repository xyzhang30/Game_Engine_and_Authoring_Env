package oogasalad.model.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * Represents the JSON data for a game object.
 *
 * @author Judy He
 */
public record GameObjectProperties(@JsonProperty("gameobject_id") int collidableId,
                                   List<String> properties,
                                   double mass,
                                   Position position,
                                   String shape,
                                   Dimension dimension,
                                   Map<String, List<Integer>> color,
                                   double staticFriction,
                                   double kineticFriction,
                                   double inclineAngle,
                                   Map<String, String> image,
                                   double direction,
                                   boolean inelastic,
                                   boolean phaser,
                                   double score
) {

}