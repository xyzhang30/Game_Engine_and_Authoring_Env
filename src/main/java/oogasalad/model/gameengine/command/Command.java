package oogasalad.model.gameengine.command;

import oogasalad.model.gameengine.GameEngine;

public interface Command {

  double execute(GameEngine engine);

}
