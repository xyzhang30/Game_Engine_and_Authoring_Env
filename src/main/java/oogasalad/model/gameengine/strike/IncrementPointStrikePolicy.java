package oogasalad.model.gameengine.strike;

import java.util.Optional;
import java.util.function.BiConsumer;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.collidable.GameObject;
import oogasalad.model.gameengine.collidable.ownable.Scoreable;

public class IncrementPointStrikePolicy implements StrikePolicy {

  @Override
  public BiConsumer<Integer, GameEngine> getStrikePolicy() {
    return (controllableId, engine) -> {
      GameObject c = engine.getCollidableContainer().getCollidable(controllableId);
      Optional<Scoreable> optionalOwnable = c.getOwnable();
      optionalOwnable.ifPresent(Scoreable::incrementTemporaryScore);
    };
  }
}
