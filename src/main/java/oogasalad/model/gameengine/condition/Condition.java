package oogasalad.model.gameengine.condition;

import oogasalad.model.gameengine.GameEngine;


/**
 * The Condition interface represents a generic condition to be evaluated in the oogasalad game
 * engine.
 * <p>
 * The purpose of this interface is to define a common structure for all conditions defined in the
 * Game Engine, allowing for the details of each individual condition to be abstracted away.
 * Condition objects are created using reflection.
 * <p>
 * This interface is a functional interface, meaning that it only provides a single method, execute,
 * which the concrete condition can query. This method returns a boolean value representing if the
 * current state of the engine satisfies the condition.
 * <p>
 * Implementations of this interface define the behavior of specific conditions
 * <p>
 * To use the Condition interface, you can create concrete implementations for specific condition.
 * For example:
 * <pre>{@code
 * public class NRoundsCompletedCondition implements Condition {
 *     public boolean evaluate(GameEngine engine) {
 *         return engine.getRound() > arguments.get(0);
 *     }
 * }
 * }</pre>
 *
 * @author Noah Loewy
 */

@FunctionalInterface
public interface Condition {

  /**
   * Evaluates the condition with the given arguments
   *
   * @param engine The instance of the game engine that the condition will query
   * @return boolean representing whether the condition is satisfied
   */

  boolean evaluate(GameEngine engine);

}
