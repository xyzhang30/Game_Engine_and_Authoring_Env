package oogasalad.model.gameengine.command;

import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;

/**
 * Represents a command to move a game object to its starting state. This command is responsible for
 * resetting a game object to its initial state.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
@ExpectedParamNumber(1)
public class MoveToStartingStateCommand implements Command {

  private final GameObject gameObject;

  /**
   * Constructs a MoveToStartingStateCommand with the specified arguments and game object map.
   *
   * @param arguments     The list of arguments for the command.
   * @param gameObjectMap a map from object ids to the actual GameObject
   */

  public MoveToStartingStateCommand(List<Integer> arguments,
      Map<Integer, GameObject> gameObjectMap) {
    gameObject = gameObjectMap.get(arguments.get(0));
  }

  /**
   * Executes the command to move a game object to its starting state. This involves resetting the
   * game object to its initial state.
   *
   * @param engine The game engine in which the command is executed.
   */

  @Override
  public void execute(GameEngine engine) {
    gameObject.toStartingState();
  }
}
