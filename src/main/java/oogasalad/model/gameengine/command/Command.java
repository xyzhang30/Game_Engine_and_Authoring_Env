package oogasalad.model.gameengine.command;

import oogasalad.model.gameengine.GameEngine;

public interface Command {

  void execute(GameEngine engine);

}
