package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

/**
 * The PlayerCompletedTurnCommand class represents a command to mark the completion of a turn by the
 * active player in the game.
 *
 * @author Noah Loewy
 */

public class PlayerCompletedTurnCommand implements Command {

  /**
   * Constructs an instance of the PlayerCompletedTurnCommand. This constructor does not actually do
   * anything, and exists for the sake of consistency across commands.
   *
   * @param arguments An empty list.
   */

  public PlayerCompletedTurnCommand(List<Double> arguments) {

  }

  /**
   * Executes the command to mark the completion of a turn by the active player. It updates the game
   * state to reflect the completion of the turn by the active player.
   *
   * @param engine The game engine instance.
   */

  @Override
  public void execute(GameEngine engine) {
    engine.getPlayerContainer().getPlayer(engine.getPlayerContainer().getActive())
        .completeTurn();
  }
}
