package oogasalad.model.api.data;

import java.util.List;

/**
 * Represents the JSON data for a static check rule in the game.
 *
 * @author Judy He
 */
public record StaticCheckRule(List<List<Object>> condition, List<List<Object>> action) {

}
