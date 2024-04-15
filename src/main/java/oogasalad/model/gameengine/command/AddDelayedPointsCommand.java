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
    double numPoints = arguments.get(1);
    for(PlayerRecord p : engine.getPlayerContainer().getPlayerRecords()) {
      if(p.myControllables().contains((int) Math.round(arguments.get(0)))) {
        engine.getPlayerContainer().getPlayer(p.playerId()).setVariable(":" + arguments.get(0),
            numPoints);
      }
    }
  }
}
