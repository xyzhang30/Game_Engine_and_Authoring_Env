package oogasalad.model.gameengine.rank;

import oogasalad.model.api.PlayerRecord;

/**
 * A comparator for sorting PlayerRecords based on the lowest score.
 */
public class LowestScoreComparator extends PlayerRecordComparator {

  /**
   * Compares two PlayerRecord objects based on their scores, sorting them in ascending order.
   *
   * @param o1 the first PlayerRecord to compare
   * @param o2 the second PlayerRecord to compare
   * @return a negative integer, zero, or a positive integer as the first argument is less than,
   * equal to, or greater than the second based on their scores.
   */
  @Override
  public int compareMe(PlayerRecord o1, PlayerRecord o2) {
    return Double.compare(o1.score(), o2.score());
  }
}