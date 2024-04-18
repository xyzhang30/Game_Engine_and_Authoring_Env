package oogasalad.model.gameengine.statichandlers;

import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.RulesRecord;

/**
 * The GameOverStaticStateHandler class represents a handler for the game over static state,
 * implementing logic to determine if the game over condition is met and take appropriate actions if
 * it is.
 *
 * @author Noah Loewy
 */
@IsCommand(isCommand = true)
public class GameOverStaticStateHandler extends StaticStateHandler {

  /**
   * Checks if the GameOver condition specified in RulesRecord is satisfied by the current game
   * state.
   *
   * @param engine The game engine instance.
   * @param rules  The rules record containing game rules and conditions.
   * @return true, if the GameOver condition is met, otherwise false.
   */
  @Override
  protected boolean canHandle(GameEngine engine, RulesRecord rules) {
    return rules.winCondition().evaluate(engine);
  }

  /**
   * Calls the engine's end game function
   *
   * @param engine The game engine instance.
   * @param rules  The rules record containing game rules and conditions.
   */
  @Override
  protected void handleIt(GameEngine engine, RulesRecord rules) {
    LOGGER.info(rules.winCondition().getClass().getSimpleName() + " (win cond) evaluated True");
    engine.endGame();
  }
}


