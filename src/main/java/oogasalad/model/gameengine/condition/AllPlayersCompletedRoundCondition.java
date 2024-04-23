package oogasalad.model.gameengine.condition;

import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.player.Player;

/**
 * The AllPlayersCompletedRoundCondition evaluates if the current round has been completed by every
 * player in the game
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class AllPlayersCompletedRoundCondition implements Condition {

  /**
   * Constructs an instance of AllPlayersCompletedRoundCondition with a list of arguments. This
   * constructor does not actually do anything, and exists for the sake of consistency across
   * conditions.
   *
   * @param arguments, an empty list
   */

  @ExpectedParamNumber(0)
  public AllPlayersCompletedRoundCondition(List<Integer> arguments,
      Map<Integer, GameObject> gameObjectMap) {

  }

  /**
   * Delegates the work to the PlayerContainers allPlayersCompletedRound function, which handles the
   * logic for determining if every player has completed the current round
   *
   * @param engine the GameEngine instance
   */

  @Override
  public boolean evaluate(GameEngine engine) {
    return engine.getPlayerContainer().getPlayers().stream().allMatch(Player::isRoundCompleted);
  }
}
