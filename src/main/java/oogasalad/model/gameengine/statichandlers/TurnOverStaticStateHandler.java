package oogasalad.model.gameengine.statichandlers;

import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.command.Command;

public class TurnOverStaticStateHandler extends GenericStaticStateHandler {

  @Override
  public boolean canHandle(GameEngine engine, RulesRecord rules) {
    return true;
  }

  @Override
  public void handleIt(GameEngine engine, RulesRecord rules) {
    for (Command cmd : rules.advanceTurn()) {
      LOGGER.info(toLogForm(cmd) + " " + "(advance) ");
      cmd.execute(engine);
    }
    if(getPrev().canHandle(engine, rules)) {
      getPrev().handleIt(engine, rules);
    }
  }
}
