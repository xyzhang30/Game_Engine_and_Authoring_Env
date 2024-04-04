package oogasalad;

import static java.util.Objects.hash;

public class Pair {

  private final int first;
  private final int second;

  public Pair(int first, int second) {
    this.first = first;
    this.second = second;
  }

  public int getFirst() {
    return first;
  }

  public int getSecond() {
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
    return hash(first, second);
  }

  public String toString() {
    return "(" + first + "," + second + ")";
  }
}
