package oogasalad.model.gameengine.statichandlers;

import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.command.Command;

/**
 * The RoundOverStaticStateHandler class represents a handler for the game over static state,
 * implementing logic to determine if the round over condition is met and take appropriate actions
 * if it is.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
public class RoundOverStaticStateHandler extends StaticStateHandler {

  /**
   * Checks if the RoundOver condition specified in RulesRecord is satisfied by the current game
   * state.
   *
   * @param engine The game engine instance.
   * @param rules  The rules record containing game rules and conditions.
   * @return true, if the RoundOver condition is met, otherwise false.
   */

  @Override
  protected boolean canHandle(GameEngine engine, RulesRecord rules) {
    return rules.roundPolicy().evaluate(engine);
  }

  /**
   * Executes all the commands specified in the game rules' AdvanceRound on the current Game State
   *
   * @param engine The game engine instance.
   * @param rules  The rules record containing game rules and conditions.
   */

  @Override
  protected void handleIt(GameEngine engine, RulesRecord rules) {
    LOGGER.info(rules.roundPolicy().getClass().getSimpleName() + " (round cond) evaluated True");
    for (Command cmd : rules.advanceRound()) {
      LOGGER.info(cmd.getClass().getSimpleName() + " (advance) ");
      cmd.execute(engine);
    }
    if (getPrev().canHandle(engine, rules)) {
      getPrev().handleIt(engine, rules);
    }
  }
}