package oogasalad.model.gameengine;

import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.gameengine.command.Command;

public record RulesRecord(int maxRounds, int maxTurns, Map<Pair, java.util.List<Command>> collisionHandlers) {}
