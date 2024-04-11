package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public class SetVisibleCommand implements Command {

  private final List<Double> arguments;

  public SetVisibleCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public void execute(GameEngine engine) {
    int obj = (int) Math.round(arguments.get(0));
    int isVisible = (int) Math.round(arguments.get(1));
    engine.getCollidableContainer().getCollidable(obj).setVisible(isVisible == 1.0);
  }
}

