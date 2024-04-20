package oogasalad.model.gameengine.rank;

import java.util.Comparator;
import oogasalad.model.api.PlayerRecord;

/**
 * An abstract class to define custom comparators for sorting PlayerRecords.
 */
public abstract class PlayerRecordComparator implements Comparator<PlayerRecord> {

  /**
   * Compares two PlayerRecord objects based on custom criteria defined in subclasses.
   * If the custom comparison result is not zero, it returns that result.
   * Otherwise, it compares the player IDs.
   *
   * @param o1 the first PlayerRecord to compare
   * @param o2 the second PlayerRecord to compare
   * @return a negative integer, zero, or a positive integer as the first argument is less than,
   * equal to, or greater than the second.
   */
  public int compare(PlayerRecord o1, PlayerRecord o2) {
    if (compareMe(o1, o2) != 0) {
      return compareMe(o1, o2);
    }
    return Integer.compare(o1.playerId(), o2.playerId());
  }

  /**
   * A method to be implemented by subclasses to define custom comparison criteria. This method
   * symbolizes the original comparison made before the ID tiebreaker.
   *
   * @param o1 the first PlayerRecord to compare
   * @param o2 the second PlayerRecord to compare
   * @return a negative integer, zero, or a positive integer if the first argument is less than,
   * equal to, or greater than the second.
   */
  protected abstract int compareMe(PlayerRecord o1, PlayerRecord o2);
}