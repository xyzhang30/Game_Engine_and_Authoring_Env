package oogasalad.model.gameengine.rank;

import oogasalad.model.annotations.IsCommand;
import oogasalad.model.api.PlayerRecord;

/**
 * A comparator for sorting PlayerRecords based on the highest score.
 *
 * @author Noah Loewy
 */
@IsCommand(isCommand = true)
public class HighestScoreComparator extends PlayerRecordComparator {

  /**
   * Compares two PlayerRecord objects based on their scores, sorting them in descending order.
   *
   * @param p1 the first PlayerRecord to compare
   * @param p2 the second PlayerRecord to compare
   * @return a negative integer, zero, or a positive integer as the first argument is greater than,
   * equal to, or less than the second based on their scores.
   */
  @Override
  public int customComparison(PlayerRecord p1, PlayerRecord p2) {
    return Double.compare(-p1.score(), -p2.score());
  }
}