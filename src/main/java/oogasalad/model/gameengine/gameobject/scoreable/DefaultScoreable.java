package oogasalad.model.gameengine.gameobject.scoreable;

import oogasalad.model.gameengine.gameobject.GameObject;

/**
 * The DefaultScoreable class represents a default implementation of the Scoreable interface.
 *
 * <p>This implementation maintains a temporary score for a GameObject and provides methods to
 * modify and retrieve this score. Additionally, it allows the GameObject to be converted into a
 * Scoreable object.
 *
 * <p>This class acts as an adapter between general GameObjects and GameObjects that are Scoreable.
 * By holding a reference to the Scoreable interface, GameObjects gain the ability to have a
 * temporary score and be treated as Scoreable objects.
 *
 * @author Noah Loewy
 */
public class DefaultScoreable implements Scoreable {

  private final GameObject gameObject;
  private double tempScore;

  /**
   * Constructs a DefaultScoreable object with the specified GameObject.
   *
   * @param go The GameObject to associate with this DefaultScoreable object.
   */
  public DefaultScoreable(GameObject go) {
    gameObject = go;
  }

  /**
   * Retrieves the temporary score of the DefaultScoreable object.
   *
   * @return The temporary score of the DefaultScoreable object.
   */

  @Override
  public double getTemporaryScore() {
    return tempScore;
  }

  /**
   * Sets the temporary score of the DefaultScoreable object.
   *
   * @param tempScore The temporary score to set.
   */
  @Override
  public void setTemporaryScore(double tempScore) {
    this.tempScore = tempScore;
  }

  /**
   * Increments the temporary score of the DefaultScoreable object by one.
   */

  @Override
  public void incrementTemporaryScore(double amt) {
    tempScore += amt;
  }

  /**
   * Converts the DefaultScoreable object into a GameObject.
   *
   * @return The GameObject representation of the DefaultScoreable object.
   */

  @Override
  public GameObject asGameObject() {
    return gameObject;
  }
}
