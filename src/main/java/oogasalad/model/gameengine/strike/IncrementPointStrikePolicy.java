package oogasalad.model.gameengine.strike;

import java.util.Optional;
import java.util.function.BiConsumer;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;

public class IncrementPointStrikePolicy implements StrikePolicy {

  @Override
  public BiConsumer<Integer, GameEngine> getStrikePolicy() {
    return (strikeableID, engine) -> {
      GameObject go = engine.getGameObjectContainer().getGameObject(strikeableID);
      Optional<Scoreable> optionalScoreable = go.getScoreable();
      optionalScoreable.ifPresent(Scoreable::incrementTemporaryScore);
    };
  }
}
