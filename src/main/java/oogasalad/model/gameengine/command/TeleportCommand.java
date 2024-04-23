package oogasalad.model.gameengine.command;

import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;

/**
 * Teleport Commands allow objects to teleport.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class TeleportCommand implements Command {

  private final List<Integer> arguments;
  private final GameObject gameObjectFrom;
  private final GameObject gameObjectTo;


  /**
   * Constructs an instance of the TeleportCommand with the provided arguments.
   *
   * @param arguments Consists of two arguments: the ID of the GameObject whose teleporting and the
   *                  game object (a surface) where the first object teleports to.
   */

  @ExpectedParamNumber(2)
  public TeleportCommand(List<Integer> arguments, Map<Integer, GameObject> gameObjectMap) {
    this.arguments = arguments;
    gameObjectFrom = gameObjectMap.get(arguments.get(0));
    gameObjectTo = gameObjectMap.get(arguments.get(1));
  }

  /**
   * Executes the command to teleport a GameObject. It teleports the game object corresponding to
   * the provided ID (argument 1) from the game engine and moves it to the location of the game
   * object of the second provided ID (argument 2)
   *
   * @param engine The game engine instance.
   */
  @Override
  public void execute(GameEngine engine) {
    gameObjectFrom.teleportTo(gameObjectTo);
  }
}
