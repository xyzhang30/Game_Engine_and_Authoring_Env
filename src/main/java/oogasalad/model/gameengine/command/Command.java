package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.api.ExternalGameEngine;

public interface Command {

  public void execute(ExternalGameEngine engine, List<Double> arguments);


}
