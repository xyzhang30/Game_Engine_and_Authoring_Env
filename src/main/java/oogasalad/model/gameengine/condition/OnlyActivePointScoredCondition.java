package oogasalad.model.gameengine.condition;

import java.util.List;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.rank.IDComparator;

public class OnlyActivePointScoredCondition implements Condition {

  @Override
  public boolean evaluate(GameEngine engine) {
    int active = engine.getPlayerContainer().getActive();
    List<PlayerRecord> lasts = engine.getPlayerContainer().getLastStatics();
    List<PlayerRecord> currents =
        engine.getPlayerContainer().getSortedPlayerRecords(new IDComparator());
    if(!engine.getPlayerContainer().getPlayer(active).getControllable().asGameObject().getVisible()){
      return false;
    }
    for(int i = 0; i < currents.size(); i++) {
      if(currents.get(i).playerId() == active ) {
        if(currents.get(i).score() == lasts.get(i).score()) {
          return false;
        }
      }
      else { //inactive
        if(currents.get(i).score() != lasts.get(i).score()) {
          return false;
        }
      }
    }
    return true;
  }
}
