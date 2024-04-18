package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;

/**
 * The MultiplySpeedCommand class represents a command to multiply the speed of a GameObject.
 *
 * @author Noah Loewy
 */
@IsCommand(isCommand = true)
public class MultiplySpeedCommand implements Command {

  private final List<Double> arguments;

  /**
   * Constructs an instance of the MultiplySpeedCommand with the provided arguments.
   *
   * @param arguments Consists of two arguments: the ID of the GameObject whose speed is to be
   *                  multiplied and the factor by which the speed should be multiplied.
   */
  @ExpectedParamNumber(2)
  public MultiplySpeedCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  /**
   * Executes the command to multiply the speed of a GameObject. It retrieves the GameObject
   * corresponding to the provided ID from the game engine and multiplies its speed by the specified
   * factor.
   *
   * @param engine The game engine instance.
   */

  @Override
  public void execute(GameEngine engine) {
    GameObject gameObject = engine.getGameObjectContainer()
        .getGameObject((int) Math.round(arguments.get(0)));
    if (gameObject != null) {
      gameObject.multiplySpeed(arguments.get(1));
    }
  }
}
