package oogasalad.model.gameengine;

import java.util.Map;
import java.util.function.Consumer;
import oogasalad.model.gameengine.GameEngine;

public record RulesRecord(int maxRounds, Map<Integer, Consumer<GameEngine>> collisionHandlers
                       //, TurnPolicy turnPolicy
) {}
