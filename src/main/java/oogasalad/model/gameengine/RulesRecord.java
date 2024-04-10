package oogasalad.model.gameengine;

import java.util.List;
import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.gameengine.collidable.PhysicsHandler;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.condition.Condition;

public record RulesRecord(Map<Pair, List<Command>> collisionHandlers,
                          Condition winCondition, Condition roundPolicy, List<Command> advanceTurn,
                          List<Command> advanceRound,
                          Map<Pair, PhysicsHandler> physicsMap) {

}
