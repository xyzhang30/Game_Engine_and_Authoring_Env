package oogasalad.model.gameengine.strike;

import java.util.function.BiConsumer;
import oogasalad.model.gameengine.GameEngine;


/**
 * The StrikePolicy functional interface defines a contract for implementing strike policies
 * in a game engine. A strike policy specifies the action to be taken when a strikeable object is
 * hit during gameplay.
 *
 * @author Noah Loewy
 */

@FunctionalInterface
public interface StrikePolicy {

  /**
   * Retrieves the strike policy as a {@code BiConsumer} function, which specifies the action
   * to be taken when a strikeable object is hit.
   *
   * @return The strike policy as a BiConsumer function, that can be called when the strikeable
   * is struck.
   */

  public BiConsumer<Integer, GameEngine> getStrikePolicy();
}
