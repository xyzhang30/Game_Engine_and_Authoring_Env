package oogasalad.model.gameengine.condition;

import oogasalad.model.api.GameRecord;
import oogasalad.model.gameengine.GameEngine;

@FunctionalInterface
public interface Condition {

  boolean evaluate(GameEngine engine);

}
