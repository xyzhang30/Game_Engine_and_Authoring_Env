package oogasalad.model.gameengine.command;

import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.CommandHelpInfo;
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
@CommandHelpInfo(description = "")
@ExpectedParamNumber(2)
public class MultiplySpeedCommand implements Command {

  private final List<Integer> arguments;
  private final GameObject gameObject;


  /**
   * Constructs an instance of the MultiplySpeedCommand with the provided arguments.
   *
   * @param arguments     Consists of two arguments: the ID of the GameObject whose speed is to be
   *                      multiplied and the factor by which the speed should be multiplied.
   * @param gameObjectMap a map from object ids to the actual GameObject
   */
  public MultiplySpeedCommand(List<Integer> arguments, Map<Integer, GameObject> gameObjectMap) {
    this.arguments = arguments;
    gameObject = gameObjectMap.get(arguments.get(0));
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
    gameObject.multiplySpeed(arguments.get(1));
  }
}
