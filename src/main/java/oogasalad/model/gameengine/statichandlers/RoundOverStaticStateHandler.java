package oogasalad.model.gameengine.statichandlers;

import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.command.Command;

public class RoundOverStaticStateHandler extends StaticStateHandler {

  @Override
  protected boolean canHandle(GameEngine engine, RulesRecord rules) {
    return rules.roundPolicy().evaluate(engine);
  }

  @Override
  protected void handleIt(GameEngine engine, RulesRecord rules) {
    LOGGER.info(rules.roundPolicy().toString().substring(rules.roundPolicy().toString().lastIndexOf(
        ".") + 1, rules.roundPolicy().toString().lastIndexOf("@")) + " (round condition) evaluated True");

    for (Command cmd : rules.advanceRound()) {
      LOGGER.info(cmd.toString().substring(cmd.toString().lastIndexOf(".") + 1,
          cmd.toString().lastIndexOf("@")) + " (advance) ");

      cmd.execute(engine);
    }
    if (getPrev().canHandle(engine, rules)) {
      getPrev().handleIt(engine, rules);
    }
  }
}