package oogasalad.model.gameengine.condition;

import oogasalad.model.gameengine.GameEngine;

public class ReturnTrueCondition implements Condition{

  @Override
  public boolean evaluate(GameEngine engine) {
    return true;
  }
}
