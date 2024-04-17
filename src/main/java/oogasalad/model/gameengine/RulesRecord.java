package oogasalad.model.gameengine;

import java.util.List;
import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.gameengine.gameobject.PhysicsHandler;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.condition.Condition;
import oogasalad.model.gameengine.statichandlers.StaticStateHandler;
import oogasalad.model.gameengine.strike.StrikePolicy;
import oogasalad.model.gameengine.turn.TurnPolicy;

/**
 * Represents a collection of game rules and configurations.
 * Includes collision handlers, win conditions, round policies, turn advancements,
 * physics mappings, turn policies, static state handlers, and strike policies.
 *
 * @param collisionHandlers    Mapping of collision pairs to lists of commands to execute on
 *                             colliding.
 * @param winCondition         Condition determining when the game is won.
 * @param roundPolicy          Condition determining when a round ends.
 * @param advanceTurn          List of commands to execute when advancing to the next turn.
 * @param advanceRound         List of commands to execute when advancing to the next round.
 * @param physicsMap           Mapping of collision pairs to physics handlers.
 * @param turnPolicy           Policy for determining which player has the next turn.
 * @param staticStateHandler   Handler for managing updating the game state after a turn.
 * @param strikePolicy         Policy for updating objects after they are struck by an external
 *                             force (e.g. hitting a golf ball).
 *
 * @author Noah Loewy
 */

public record RulesRecord(Map<Pair, List<Command>> collisionHandlers,
                          Condition winCondition, Condition roundPolicy, List<Command> advanceTurn,
                          List<Command> advanceRound,
                          Map<Pair, PhysicsHandler> physicsMap, TurnPolicy turnPolicy,
                          StaticStateHandler staticStateHandler, StrikePolicy strikePolicy) {


}
