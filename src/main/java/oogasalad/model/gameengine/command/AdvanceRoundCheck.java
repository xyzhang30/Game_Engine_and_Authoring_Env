package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.Player;

public class AdvanceRoundCheck implements Command{

  public AdvanceRoundCheck(List<Double> arguments){

  }

  @Override
  public double execute(GameEngine engine) {
    return engine.getPlayerContainer().allPlayersCompletedRound() ? 1.0 : 0.0;
  }
}
