package oogasalad.model;

import static java.util.Objects.hash;

import oogasalad.model.gameengine.gameobject.GameObject;

/**
 * Represents a pair of GameObject instances. This class is used to store pairs of GameObjects for
 * various purposes.
 *
 * @author Noah Loewy
 */

public class Pair {

  private final GameObject first;
  private final GameObject second;

  /**
   * Constructs a Pair object with the specified GameObjects.
   *
   * @param first  The first GameObject in the pair.
   * @param second The second GameObject in the pair.
   */
  public Pair(GameObject first, GameObject second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Retrieves the first GameObject in the pair.
   *
   * @return The first GameObject in the pair.
   */
  public GameObject getFirst() {
    return first;
  }

  /**
   * Retrieves the second GameObject in the pair.
   *
   * @return The second GameObject in the pair.
   */
  public GameObject getSecond() {
    return second;
  }

  /**
   * Checks if this Pair is equal to another object. Two Pairs are considered equal if they contain
   * the same GameObjects, regardless of the order.
   *
   * @param o The object to compare with this Pair.
   * @return true if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pair pair = (Pair) o;
    return (first == pair.first && second == pair.second) || (first == pair.second
        && second == pair.first);
  }

  /**
   * Generates a hash code for this Pair. The hash code is based on the IDs of the contained
   * GameObjects.
   *
   * @return The hash code value for this Pair.
   */
  @Override
  public int hashCode() {
    return hash(Math.min(first.getId(), second.getId()), Math.max(first.getId(), second.getId()));
  }
}
