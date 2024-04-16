package oogasalad.model.gameengine.strike;

import java.util.function.BiConsumer;
import oogasalad.model.gameengine.GameEngine;

public class IncrementPointStrikePolicy implements StrikePolicy {

  @Override
  public BiConsumer<Integer, GameEngine> getStrikePolicy() {
    return (controllableId, engine) -> {
      engine.getCollidableContainer().getCollidable((int) Math.round(controllableId)).getOwnable().incrementTemporaryScore();
    };
  }
}
