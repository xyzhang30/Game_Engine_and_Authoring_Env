package oogasalad.model.gameengine.command;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.player.Player;
import oogasalad.model.gameengine.rank.IDComparator;

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
@ExpectedParamNumber(0)
public class ConditionalAdvanceTurnCommand implements Command {

  public ConditionalAdvanceTurnCommand(List<Integer> arguments,
      Map<Integer, GameObject> gameObjectMap) {
    //do nothing
  }

  /**
   * Executes the command to conditionally advance the turn in the provided game engine. The turn is
   * advanced if certain conditions are met. This command refers specifically to billiards, and
   * advances the turn as long as the player did not get one of their balls in, there was no
   * scratch, and no opponent balls went in. These conditions can eventually be abstracted out, time
   * permitted.
   *
   * @param engine The game engine in which the command is executed.
   */


  @Override
  public void execute(GameEngine engine) {
    List<PlayerRecord> lasts = engine.getPlayerContainer().getPlayers().stream()
        .map(Player::getLastPlayerRecord)
        .sorted(Comparator.comparing(PlayerRecord::playerId))
        .toList();
    List<PlayerRecord> currents =
        engine.getPlayerContainer().getPlayers().stream()
            .map(Player::getPlayerRecord)
            .sorted(new IDComparator())
            .toList();
    if (!engine.getPlayerContainer().getActive().getStrikeable().asGameObject().getVisible()
        || IntStream.range(0, currents.size())
        .anyMatch(
            i -> (currents.get(i).playerId() == engine.getPlayerContainer().getActive().getId())
                == (currents.get(i).score() == lasts.get(i).score()))) {
      engine.advanceTurn();
    }

  }
}

