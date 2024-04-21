package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;


@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class MoveToStartingStateCommand implements Command {

  private final List<Double> arguments;

  @ExpectedParamNumber(1)
  public MoveToStartingStateCommand(List<Double> arguments) {
    this.arguments = arguments;
  }


  @Override
  public void execute(GameEngine engine) {
    GameObject gameObject = engine.getGameObjectContainer()
        .getGameObject((int) Math.round(arguments.get(0)));
    gameObject.toStartingState();
  }
}
