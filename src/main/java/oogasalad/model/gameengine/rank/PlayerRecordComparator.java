package oogasalad.model.gameengine.rank;

import java.util.Comparator;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.api.PlayerRecord;

/**
 * An abstract class to define custom comparators for sorting PlayerRecords.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = false)
public abstract class PlayerRecordComparator implements Comparator<PlayerRecord> {

  /**
   * Compares two PlayerRecord objects based on custom criteria defined in subclasses. If the custom
   * comparison result is not zero, it returns that result. Otherwise, it compares the player IDs.
   *
   * @param p1 the first PlayerRecord to compare
   * @param p2 the second PlayerRecord to compare
   * @return a negative integer, zero, or a positive integer as the first argument is less than,
   * equal to, or greater than the second.
   */
  public int compare(PlayerRecord p1, PlayerRecord p2) {
    int result = customComparison(p1, p2);
    return result != 0 ? result : Integer.compare(p1.playerId(), p2.playerId()); //tiebreaker
  }

  /**
   * A method to be implemented by subclasses to define custom comparison criteria. This method
   * symbolizes the original comparison made before the ID tiebreaker.
   *
   * @param p1 the first PlayerRecord to compare
   * @param p2 the second PlayerRecord to compare
   * @return a negative integer, zero, or a positive integer if the first argument is less than,
   * equal to, or greater than the second.
   */
  protected abstract int customComparison(PlayerRecord p1, PlayerRecord p2);
}