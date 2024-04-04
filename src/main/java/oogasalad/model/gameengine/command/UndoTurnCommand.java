package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class UndoTurnCommand implements Command {

  private List<Double> arguments;

  public UndoTurnCommand(List<Double> arguments) {
    this.arguments = arguments;
  }
  @Override
  public double execute(GameEngine engine) {
    System.out.println(103333);
    engine.toLastStaticState();

    return 1.0;
  }


}
