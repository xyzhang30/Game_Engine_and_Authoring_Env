package oogasalad.model.gameengine;

import java.util.List;
import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.gameengine.collidable.PhysicsHandler;
import oogasalad.model.gameengine.command.Command;

public record RulesRecord(int maxRounds, int maxTurns, Map<Pair, List<Command>> collisionHandlers,
                          Command winCondition, List<Command> advance,
                          Map<Pair, PhysicsHandler> physicsMap) {

}
