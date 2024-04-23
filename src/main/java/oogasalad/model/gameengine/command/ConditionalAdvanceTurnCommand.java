package oogasalad.model.gameengine.command;

import static java.util.Collections.sort;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.rank.IDComparator;

public class ConditionalAdvanceTurnCommand implements Command {

  @ExpectedParamNumber(0)
  public ConditionalAdvanceTurnCommand(List<Integer> arguments, Map<Integer, GameObject> gameObjectMap) {

  }

  @Override
  public void execute(GameEngine engine) {
    int active = engine.getPlayerContainer().getActive();
    List<PlayerRecord> lasts = engine.getPlayerContainer().getPlayers().stream()
        .map(Player::getLastPlayerRecord)
        .sorted(Comparator.comparing(PlayerRecord::playerId))
        .toList();
    List<PlayerRecord> currents =
        engine.getPlayerContainer().getPlayerRecords().stream()
            .sorted(new IDComparator())
            .toList();
    if(!engine.getPlayerContainer().getPlayer(active).getStrikeable().asGameObject().getVisible()){
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

