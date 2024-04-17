package oogasalad.model.gameengine.statichandlers;

import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.RulesRecord;

public class GameOverStaticStateHandler extends StaticStateHandler {

  @Override
  protected boolean canHandle(GameEngine engine, RulesRecord rules) {
    return rules.winCondition().evaluate(engine);
  }

  @Override
  protected void handleIt(GameEngine engine, RulesRecord rules) {
    if (rules.winCondition().evaluate(engine)) {
      LOGGER.info(rules.winCondition().toString().substring(rules.winCondition().toString().lastIndexOf(
          ".") + 1, rules.winCondition().toString().lastIndexOf("@")) + " (win condition) evaluated "
          + "True");
      engine.endGame();
    }
  }


}
