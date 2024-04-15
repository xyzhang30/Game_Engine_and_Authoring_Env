package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class PlayerCompletedTurnCommand implements Command {

  private final List<Double> arguments;

  public PlayerCompletedTurnCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public void execute(GameEngine engine) {
    engine.getPlayerContainer().getPlayer(engine.getPlayerContainer().getActive())
        .completeTurn();
  }
}
