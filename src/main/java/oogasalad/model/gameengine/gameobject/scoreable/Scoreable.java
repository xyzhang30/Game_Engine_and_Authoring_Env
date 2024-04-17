package oogasalad.model.gameengine.gameobject.scoreable;

import oogasalad.model.gameengine.gameobject.GameObject;

/**
 * The Scoreable interface represents objects in the game engine that can have a temporary score.
 *
 * <p>Objects implementing this interface can have their temporary score modified and retrieved. They
 * also have the capability to be converted into a GameObject.
 *
 * <p> Objects that implement this interface would enclude Golf Balls, Soccer Goal's, and Shuffle
 * Boarding Weights, as their relative location/state can result in points being applied
 *
 * <p>The adapter pattern is used to bridge the gap between general GameObjects and GameObjects
 * that are Scoreable. By holding a reference to the Scoreable, GameObjects have the ability to
 * have a temporary score and be treated as Scoreable objects. This allows for a flexible and
 * modular design, where GameObjects can seamlessly integrate with components that require
 * Scoreable behavior.
 *
 * <p>The adapter pattern promotes code reusability and adhere to the Single Responsibility
 * Principle, Encapsulation, and Open-Closed principles, as GameObjects can focus on their
 * primary functionality while still being adaptable to Scoreable behavior when needed.
 *
 * @author Noah Loewy
 */


public interface Scoreable {

  /**
   * Sets the temporary score of the Scoreable object.
   *
   * @param tempScore The temporary score to set.
   */

  public void setTemporaryScore(double tempScore);

  /**
   * Retrieves the temporary score of the Scoreable object.
   *
   * @return The temporary score of the Scoreable object.
   */

  public double getTemporaryScore();

  /**
   * Increments the temporary score of the Scoreable object by a default value.
   */

  public void incrementTemporaryScore();

  /**
   * Converts the Scoreable object into a GameObject.
   *
   * @return The GameObject representation of the Scoreable object.
   */

  public GameObject asGameObject();


}
