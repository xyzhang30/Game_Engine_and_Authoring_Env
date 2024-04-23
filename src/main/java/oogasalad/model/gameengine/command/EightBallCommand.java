package oogasalad.model.gameengine.command;

import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.player.Player;

public class EightBallCommand implements Command {

  private final List<Integer> arguments;
  private final GameObject gameObject;

  @ExpectedParamNumber(value = 1, paramDescription = {"(double) game object ID of 8 ball"})
  public EightBallCommand(List<Integer> arguments, Map<Integer, GameObject> gameObjectMap) {
    this.arguments = arguments;
    gameObject = gameObjectMap.get(arguments.get(0));

  }

  @Override
  public void execute(GameEngine engine) {
    gameObject.setVisible(false);
    int active = engine.getPlayerContainer().getActive();
    engine.getGameObjectContainer().getGameObjects().forEach(GameObject::stop);
    boolean didActiveWin =
        engine.getPlayerContainer().getPlayer(active).areAllScoreablesInvisible();
    for (Player p : engine.getPlayerContainer().getPlayers()) {
          p.applyGameResult((active == p.getId()) == didActiveWin);
    }
  }
}
