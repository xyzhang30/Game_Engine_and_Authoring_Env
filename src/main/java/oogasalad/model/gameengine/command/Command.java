package oogasalad.model.gameengine.command;

import oogasalad.model.api.ExternalGameEngine;

public interface Command {

  public void execute(ExternalGameEngine engine, int id1, int id2);
  public void execute(ExternalGameEngine engine);

}
