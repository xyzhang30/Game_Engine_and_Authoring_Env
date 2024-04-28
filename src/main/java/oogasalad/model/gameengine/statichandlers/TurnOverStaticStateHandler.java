package oogasalad.model.gameengine.statichandlers;

import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.RulesRecord;

/**
 * The TurnOverStaticStateHandler class represents a handler for the game over static state, which
 * takes action when it is detected that a turn is over.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class TurnOverStaticStateHandler extends StaticStateHandler {

  /**
   * The canHandle method always returns true, as it signifies the turn must be over if a static
   * state is reached and the round/game is not over.
   *
   * @param engine The game engine instance.
   * @param rules  The rules record containing game rules and conditions.
   * @return true
   */

  @Override
  protected boolean canHandle(GameEngine engine, RulesRecord rules) {
    return true;
  }

  /**
   * Executes all the commands specified in the game rules' AdvanceTurn on the current Game State.
   *
   * @param engine The game engine instance.
   * @param rules  The rules record containing game rules and conditions.
   */

  @Override
  protected void handleIt(GameEngine engine, RulesRecord rules) {
    executeCommands(rules.advanceTurn(), engine, rules);
  }
}
