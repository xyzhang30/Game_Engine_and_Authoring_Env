package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;

public class AddDelayedPointsCommand implements Command {

  private final List<Double> arguments;

  public AddDelayedPointsCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public void execute(GameEngine engine) {
    engine.getCollidableContainer().getCollidable((int) Math.round(arguments.get(0))).getOwnable().setTemporaryScore(arguments.get(1));
  }
}
