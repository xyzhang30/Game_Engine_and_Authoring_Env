package oogasalad.model.gameengine.command;

import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;

/**
 * The PlayerCompletedTurnCommand class represents a command to mark the completion of a turn by the
 * active player in the game.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
@ExpectedParamNumber(0)
public class PlayerCompletedTurnCommand implements Command {

  /**
   * Constructs an instance of the PlayerCompletedTurnCommand. This constructor does not actually do
   * anything, and exists for the sake of consistency across commands.
   *
   * @param arguments An empty list.
   * @param gameObjectMap a map from object ids to the actual GameObject
   */

  public PlayerCompletedTurnCommand(List<Integer> arguments,
      Map<Integer, GameObject> gameObjectMap) {

  }

  /**
   * Executes the command to mark the completion of a turn by the active player. It updates the game
   * state to reflect the completion of the turn by the active player.
   *
   * @param engine The game engine instance.
   */

  @Override
  public void execute(GameEngine engine) {
    engine.getPlayerContainer().getActive().completeTurn();
  }
}
