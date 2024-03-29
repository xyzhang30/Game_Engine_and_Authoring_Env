package oogasalad.model;

import java.util.Map;
import java.util.function.Consumer;

record RulesRecord(int maxRounds, Map<Integer, Consumer<GameEngine>> collisionHandlers
                       //, TurnPolicy turnPolicy
) {}
