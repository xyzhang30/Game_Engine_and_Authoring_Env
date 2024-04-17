package oogasalad.model.gameengine.strike;

import java.util.function.BiConsumer;
import oogasalad.model.gameengine.GameEngine;

public class DoNothingStrikePolicy implements StrikePolicy {

  @Override
  public BiConsumer<Integer, GameEngine> getStrikePolicy() {
    return (strikeableID, engine) -> {}; //do nothing
  }
}
