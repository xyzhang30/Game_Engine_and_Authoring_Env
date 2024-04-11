package oogasalad.model.gameengine.statichandlers;

import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.RulesRecord;

public interface StaticStateHandler {

  void handle(GameEngine engine, RulesRecord rules);

}
