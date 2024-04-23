package oogasalad.model.gameengine.command;

import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;


@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class MoveToStartingStateCommand implements Command {

  private final List<Integer> arguments;
  private final GameObject gameObject;


  @ExpectedParamNumber(1)
  public MoveToStartingStateCommand(List<Integer> arguments, Map<Integer, GameObject> gameObjectMap) {
    this.arguments = arguments;
    gameObject = gameObjectMap.get(arguments.get(0));

  }


  @Override
  public void execute(GameEngine engine) {
    gameObject.toStartingState();
  }
}
