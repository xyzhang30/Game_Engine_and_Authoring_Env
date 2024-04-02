package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class AdvanceTurnCommand implements Command {

  @Override
  public double execute(GameEngine engine, List<Double> arguments) {
    engine.advanceTurn();
    return 0.0;
  }

}
