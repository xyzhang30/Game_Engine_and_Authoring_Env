package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.rank.IDComparator;

public class ConditionalAdvanceTurnCommand implements Command {

  @ExpectedParamNumber(0)
  public ConditionalAdvanceTurnCommand(List<Double> arguments) {

  }

  @Override
  public void execute(GameEngine engine) {
    int active = engine.getPlayerContainer().getActive();
    List<PlayerRecord> lasts = engine.getPlayerContainer().getLastStatics();
    List<PlayerRecord> currents =
        engine.getPlayerContainer().getSortedPlayerRecords(new IDComparator());
    if(!engine.getGameObjectContainer().getGameObject(engine.getPlayerContainer().getPlayer(active).getStrikeableID()).getVisible()){
      engine.advanceTurn();
      return;
    }
    for(int i = 0; i < currents.size(); i++) {
      if(currents.get(i).playerId() == active ) {
        if(currents.get(i).score() == lasts.get(i).score()) {
          engine.advanceTurn();
          return;
        }
      }
      else { //inactive
        if(currents.get(i).score() != lasts.get(i).score()) {
          engine.advanceTurn();
          return;
        }
      }
    }
  }
}

