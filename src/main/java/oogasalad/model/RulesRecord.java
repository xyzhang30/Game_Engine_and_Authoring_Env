package oogasalad.model;

import java.util.Map;
import java.util.function.Consumer;
import oogasalad.Pair;
import oogasalad.model.gameengine.GameEngine;

record RulesRecord(int maxRounds, Map<Pair, Consumer<GameEngine>> collisionHandlers
                   //, TurnPolicy turnPolicy
) {

}
