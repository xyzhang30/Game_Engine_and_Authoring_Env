package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class UndoTurnCommand implements Command {

  private final List<Double> arguments;

  public UndoTurnCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public void execute(GameEngine engine) {
    engine.toLastStaticState();
  }


}
