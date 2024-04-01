package oogasalad.model.gameengine.logic;

import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.gameengine.command.Command;

public record RulesRecord(int maxRounds, int maxTurns, Map<Pair, Command> collisionHandlers) {}
