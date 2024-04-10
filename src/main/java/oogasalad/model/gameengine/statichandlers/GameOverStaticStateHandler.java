package oogasalad.model.gameengine.statichandlers;

import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.RulesRecord;

public class GameOverStaticStateHandler extends GenericStaticStateHandler {

  @Override
  public boolean canHandle(GameEngine engine, RulesRecord rules) {
    return rules.roundPolicy().evaluate(engine);
  }

  @Override
  public void handleIt(GameEngine engine, RulesRecord rules) {
    if (rules.winCondition().evaluate(engine)) {
      LOGGER.info(toLogForm(rules.winCondition()) + " (win " + "condition) evaluated True");
      engine.endGame();
    }
  }



}
