package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class PlayerCompletedRoundCommand implements Command {

  private final List<Double> arguments;

  public PlayerCompletedRoundCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public double execute(GameEngine engine) {
    engine.getPlayerContainer().getPlayer((int) Math.round(arguments.get(0)))
        .setRoundCompleted(true);
    return 0.0;
  }
}
