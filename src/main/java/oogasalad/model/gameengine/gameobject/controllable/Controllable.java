package oogasalad.model.gameengine.gameobject.controllable;

import oogasalad.model.gameengine.gameobject.GameObject;


/**
 * Represents an interface for controllable game objects, which can be moved along the x and/or y
 * axis.
 *
 * @author Noah Loewy
 */
public interface Controllable {

  /**
   * Moves the controllable object along the x-axis.
   *
   * @param positive True if the movement is in the positive direction, false otherwise.
   * @return The amount of movement along the x-axis.
   */

  double moveX(boolean positive);

  /**
   * Moves the controllable object along the y-axis.
   */

  double moveY();

  /**
   * Retrieves the controllable object as a GameObject.
   *
   * @return The controllable object as a GameObject.
   */

  GameObject asGameObject();

  /**
   * Sets the amount for the controllable object to move by for each prompt.
   *
   * @param movementXaxis The amount to move along the x-axis.
   */

  void setMovement(int movementXaxis, int movementYaxis);
}