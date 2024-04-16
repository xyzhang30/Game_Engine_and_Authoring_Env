package oogasalad.model.gameengine.strike;

import java.util.function.BiConsumer;
import oogasalad.model.gameengine.GameEngine;

@FunctionalInterface
public interface StrikePolicy {
  public BiConsumer<Integer, GameEngine> getStrikePolicy();
}
