package oogasalad.model.gameparser.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents the JSON data for a collidable object in the game.
 *
 * @author Judy He
 */
public record CollidableObject(@JsonProperty("collidable_id") int collidableId,
                        List<String> properties,
                        double mass,
                        Position position,
                        String shape,
                        Dimension dimension,
                        List<Integer> color,
                        double friction) {}