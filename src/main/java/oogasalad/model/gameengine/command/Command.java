package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;

public interface Command {

  public double execute(GameEngine engine, List<Double> arguments);

}
