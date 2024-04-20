package oogasalad.model.gameengine.rank;

import oogasalad.model.api.PlayerRecord;

public class HighestScoreComparator extends PlayerRecordComparator {

  @Override
  public int compareMe(PlayerRecord o1, PlayerRecord o2) {
    return Double.compare(-o1.score(), -o2.score());
  }

}
