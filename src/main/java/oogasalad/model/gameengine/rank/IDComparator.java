package oogasalad.model.gameengine.rank;

import oogasalad.model.api.PlayerRecord;

/**
 * A comparator for sorting PlayerRecords based on their player IDs.
 */
public class IDComparator extends PlayerRecordComparator {

  /**
   * Compares two PlayerRecord objects based on their player IDs.
   *
   * @param o1 the first PlayerRecord to compare
   * @param o2 the second PlayerRecord to compare
   * @return zero. This is a no-op, as we will compare based on their IDs, as the tiebreaker
   * specifies.
   */
  @Override
  protected int compareMe(PlayerRecord o1, PlayerRecord o2) {
    return 0;
  }
}