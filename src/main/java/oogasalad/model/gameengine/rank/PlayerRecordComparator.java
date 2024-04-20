package oogasalad.model.gameengine.rank;

import java.util.Comparator;
import oogasalad.model.api.PlayerRecord;

public abstract class PlayerRecordComparator implements
    Comparator<PlayerRecord> {
  public int compare(PlayerRecord o1, PlayerRecord o2) {
    if (compareMe(o1, o2)!=0) {
      System.out.println(o1.score() + " " + o2.score() + " " + compareMe(o1,o2));
      return compareMe(o1, o2);
    };
    return Integer.compare(o1.playerId(), o2.playerId());
  }

  protected abstract int compareMe(PlayerRecord o1, PlayerRecord o2);

}
