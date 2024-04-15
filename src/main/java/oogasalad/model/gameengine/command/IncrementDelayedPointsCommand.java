package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;

public class IncrementDelayedPointsCommand implements Command {

  private final List<Double> arguments;

  public IncrementDelayedPointsCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public void execute(GameEngine engine) {
    engine.getCollidableContainer().getCollidable((int) Math.round(arguments.get(0))).getOwnable().incrementTemporaryScore();
  }
}
