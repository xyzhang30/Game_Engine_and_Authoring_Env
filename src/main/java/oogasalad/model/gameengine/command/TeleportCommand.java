package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.gameengine.GameEngine;

/**
 * Teleport Commands allow objects to teleport.
 *
 * @author Noah Loewy
 */

public class TeleportCommand implements Command {

  private final List<Double> arguments;

  /**
   * Constructs an instance of the TeleportCommand with the provided arguments.
   *
   * @param arguments Consists of two arguments: the ID of the GameObject whose teleporting and
   *                  the game object (a surface) where the first object teleports to.
   */

  @ExpectedParamNumber(2)
  public TeleportCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  /**
   * Executes the command to teleport a GameObject. It teleports the game object
   * corresponding to the provided ID (argument 1) from the game engine and moves it to the
   * location of the game object of the second provided ID (argument 2)
   *
   * @param engine The game engine instance.
   */
  @Override
  public void execute(GameEngine engine) {
    engine.getGameObjectContainer().teleport((int) Math.round(arguments.get(0)),
        (int) Math.round(arguments.get(1)));
  }
}
