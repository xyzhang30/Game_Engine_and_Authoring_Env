package oogasalad.model.gameengine;

import java.util.Map;
import java.util.function.Consumer;
import oogasalad.Pair;

public record RulesRecord(int maxRounds, Map<Pair, Consumer<GameEngine>> collisionHandlers,
                          Condition endRoundCondition, Condition endTurnCondition,
                          Condition endSubTurnCondition) {}
