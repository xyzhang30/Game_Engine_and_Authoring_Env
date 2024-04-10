package oogasalad.model.gameengine.statichandlers;

import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.command.Command;

public class RoundOverStaticStateHandler extends GenericStaticStateHandler {

  @Override
  public boolean canHandle(GameEngine engine, RulesRecord rules) {
    return rules.roundPolicy().evaluate(engine);
  }

  @Override
  public void handleIt(GameEngine engine, RulesRecord rules) {
    LOGGER.info(toLogForm(rules.roundPolicy()) + " (round condition) evaluated True");
    engine.advanceRound();
    if(getPrev().canHandle(engine,rules)) {
      getPrev().handleIt(engine, rules);
    }
  }
}