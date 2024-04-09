package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class AdvanceRoundCheck implements Command{

  public AdvanceRoundCheck(List<Double> arguments){

  }

  @Override
  public double execute(GameEngine engine) {
    //checks
    return 0.0;
  }
}
