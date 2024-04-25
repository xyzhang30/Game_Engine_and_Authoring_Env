package oogasalad.model.gameengine.gameobject.controllable;

import oogasalad.model.gameengine.gameobject.GameObject;

public class DefaultControllable implements Controllable {

  private final GameObject go;
  private int xMovement;
  private int yMovement;

  public DefaultControllable(GameObject go) {
    this.go = go;
    xMovement = 0;
    yMovement = 0;
  }

  /**
   * Moves the controllable object along the x-axis.
   *
   * @param positive True if the movement is in the positive direction, false otherwise.
   * @return The amount of movement along the x-axis.
   */

  @Override
  public double moveX(boolean positive) {
    return xMovement * (positive ? 1 : -1);
  }

  /**
   * Moves the controllable object along the y-axis.
   *
   * @param positive True if the movement is in the positive direction, false otherwise.
   * @return The amount of movement along the y-axis.
   */

  @Override
  public double moveY(boolean positive) {
    return yMovement * (positive ? 1 : -1);
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
   * @param xMovement The amount to move along the x-axis.
   * @param yMovement The amount to move along the y-axis.
   */

  @Override
  public void setMovement(int xMovement, int yMovement) {
    this.xMovement = xMovement;
    this.yMovement = yMovement;
  }
}




