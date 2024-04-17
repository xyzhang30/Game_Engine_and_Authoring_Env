package oogasalad.model.gameengine.statichandlers;

import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.command.Command;

public class TurnOverStaticStateHandler extends StaticStateHandler {

  @Override
  protected boolean canHandle(GameEngine engine, RulesRecord rules) {
    return true;
  }

  @Override
  protected void handleIt(GameEngine engine, RulesRecord rules) {
    for (Command cmd : rules.advanceTurn()) {
      LOGGER.info( cmd.toString().substring(cmd.toString().lastIndexOf(
          ".") + 1, cmd.toString().lastIndexOf("@")) + " (win condition) evaluated "
          + "True" + " (advance) ");
      cmd.execute(engine);
    }
    if (getPrev().canHandle(engine, rules)) {
      getPrev().handleIt(engine, rules);
    }
  }
}
