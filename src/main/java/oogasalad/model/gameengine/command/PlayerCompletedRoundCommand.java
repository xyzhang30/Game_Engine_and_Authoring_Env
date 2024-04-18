package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;

/**
 * The PlayerCompletedRoundCommand class represents a command to mark the completion of a round by
 * the active player in the game.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
public class PlayerCompletedRoundCommand implements Command {

  /**
   * Constructs an instance of the PlayerCompletedRoundCommand. This constructor does not actually
   * do anything, and exists for the sake of consistency across commands.
   *
   * @param arguments An empty list.
   */

  @ExpectedParamNumber(0)
  public PlayerCompletedRoundCommand(List<Double> arguments) {

  }

  /**
   * Executes the command to mark the completion of a round by the active player. It updates the
   * game state to reflect the completion of the round by the active player.
   *
   * @param engine The game engine instance.
   */
  @Override
  public void execute(GameEngine engine) {
    engine.getPlayerContainer().getPlayer(engine.getPlayerContainer().getActive()).completeRound();
  }
}
