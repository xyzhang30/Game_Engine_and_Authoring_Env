package oogasalad;

import static java.util.Objects.hash;

import oogasalad.model.gameengine.gameobject.GameObject;

public class Pair {

  private final GameObject first;
  private final GameObject second;

  public Pair(GameObject first, GameObject second) {
    this.first = first;
    this.second = second;
  }

  public GameObject getFirst() {
    return first;
  }

  public GameObject getSecond() {
    return second;
  }

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

  @Override
  public int hashCode() {
    return hash(Math.min(first.getId(), second.getId()), Math.max(first.getId(), second.getId()));
  }

  public String toString() {
    return "(" + first.getId() + "," + second.getId() + ")";
  }
}
//