package oogasalad.model.gameengine.command;

import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.player.Player;

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
@ExpectedParamNumber(value = 1, paramDescription = {"(int) game object ID of 8 ball"})
public class EightBallCommand implements Command {

  private final List<Integer> arguments;
  private final GameObject gameObject;

  public EightBallCommand(List<Integer> arguments, Map<Integer, GameObject> gameObjectMap) {
    this.arguments = arguments;
    gameObject = gameObjectMap.get(arguments.get(0));

  }

  @Override
  public void execute(GameEngine engine) {
    gameObject.setVisible(false);
    engine.getGameObjectContainer().getGameObjects().forEach(GameObject::stop);
    boolean didActiveWin = engine.getPlayerContainer().getActive().areAllScoreablesInvisible();
    for (Player p : engine.getPlayerContainer().getPlayers()) {
      p.applyGameResult(
          (engine.getPlayerContainer().getActive().getId() == p.getId()) == didActiveWin);
    }
  }
}
