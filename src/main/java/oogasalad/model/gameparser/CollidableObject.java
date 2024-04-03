package oogasalad.model.gameparser;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

record CollidableObject(@JsonProperty("collidable_id") int collidableId,
                        List<String> properties,
                        double mass,
                        Position position,
                        String shape,
                        Dimension dimension,
                        List<Integer> color,
                        double friction) {}