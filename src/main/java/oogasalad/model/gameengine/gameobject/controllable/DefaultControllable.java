package oogasalad.model.gameengine.gameobject.controllable;

import oogasalad.model.gameengine.gameobject.GameObject;

/**
 * The DefaultControllable class represents a default implementation of the Controllable interface.
 *
 * <p>This implementation allows for GameObjects to be controlled by the user, and move along
 * both the x and y axis.
 *
 * <p>This class acts as an adapter between general GameObjects and GameObjects that are
 * Controllable. By holding a reference to the Controllable interface, GameObjects gain the ability
 * to have a temporary score and be treated as Controllable objects.
 *
 * @author Noah Loewy
 */

public class DefaultControllable implements Controllable {

  private final GameObject go;
  private int movementXaxis;

  /**
   * Constructs a DefaultControllable object with the specified GameObject.
   *
   * @param go The GameObject to associate with this DefaultControllable object.
   */

  public DefaultControllable(GameObject go) {
    this.go = go;
    movementXaxis = 0;
  }

  /**
   * Moves the controllable object along the x-axis.
   *
   * @param positive True if the movement is in the positive direction, false otherwise.
   * @return The amount of movement along the x-axis.
   */

  @Override
  public double moveX(boolean positive) {
    return movementXaxis * (positive ? 1 : -1);
  }

  /**
   * Retrieves the controllable object as a GameObject.
   *
   * @return The controllable object as a GameObject.
   */

  @Override
  public GameObject asGameObject() {
    return go;
  }

  /**
   * Sets the amount for the controllable object to move by for each prompt.
   *
   * @param movementXaxis The amount to move along the x-axis.
   */

  @Override
  public void setMovement(int movementXaxis) {
    this.movementXaxis = movementXaxis;
  }
}




