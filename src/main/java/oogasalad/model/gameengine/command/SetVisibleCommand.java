package oogasalad.model.gameengine.command;

import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;

/**
 * The SetVisibleCommand class represents a command to set the visibility of a GameObject.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
@ExpectedParamNumber(2)
public class SetVisibleCommand implements Command {

  private final List<Integer> arguments;
  private final GameObject gameObject;


  /**
   * Constructs an instance of the SetVisibleCommand with the provided arguments.
   *
   * @param arguments Consists of two arguments: the ID of the GameObject whose visibility is to be
   *                  updated, and a boolean flag representing whether visibility should be set to
   *                  true or false.
   */

  public SetVisibleCommand(List<Integer> arguments, Map<Integer, GameObject> gameObjectMap) {
    this.arguments = arguments;
    gameObject = gameObjectMap.get(arguments.get(0));

  }

  /**
   * Executes the command to set the visibility of a GameObject. It retrieves the GameObject
   * corresponding to the provided ID (argument 1) from the game engine and sets its visibility
   * based on the second argument.
   *
   * @param engine The game engine instance.
   */

  @Override
  public void execute(GameEngine engine) {
    gameObject.setVisible(arguments.get(1) == 1);
  }
}

