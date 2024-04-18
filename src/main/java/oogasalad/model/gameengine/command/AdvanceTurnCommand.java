package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;

/**
 * The AdvanceYutnCommand handles the internal transition from one turn to another in the ooga salad
 * game engine
 *
 * @author Noah Loewy
 */
@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class AdvanceTurnCommand implements Command {

  private final List<Double> arguments;

  /**
   * Constructs an instance of AdvanceTurnCommand with a list of arguments. This constructor does
   * not actually do anything, and exists for the sake of consistency across commands.
   *
   * @param arguments, an empty list
   */

  @ExpectedParamNumber(0)
  public AdvanceTurnCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  /**
   * Delegates the work to the engine advanceTurn function, which handles the logic for actually
   * updating the current turn in the game.
   *
   * @param engine the GameEngine instance
   */

  @Override
  public void execute(GameEngine engine) {
    engine.advanceTurn();
  }

}
