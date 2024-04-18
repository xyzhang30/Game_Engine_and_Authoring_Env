package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;

/**
 * The AdvanceRoundCommand handles the internal transition from one round to another in the ooga
 * salad game engine
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
public class AdvanceRoundCommand implements Command {

  /**
   * Constructs an instance of AdvanceRoundCommand with a list of arguments. This constructor does
   * not actually do anything, and exists for the sake of consistency across commands.
   *
   * @param arguments, an empty list
   */

  @ExpectedParamNumber(0)
  public AdvanceRoundCommand(List<Double> arguments) {

  }

  /**
   * Delegates the work to the engine advanceRound function, which handles the logic for actually
   * updating the current round in the game.
   *
   * @param engine the GameEngine instance
   */

  @Override
  public void execute(GameEngine engine) {
    engine.advanceRound();
  }
}
