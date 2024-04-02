package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class AdvanceRoundCommand implements Command {

  @Override
  public void execute(GameEngine engine, List<Double> arguments) {
    engine.advanceRound();
  }
}
