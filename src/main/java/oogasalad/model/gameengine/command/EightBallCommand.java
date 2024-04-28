package oogasalad.model.gameengine.command;

import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;

/**
 * The AdvanceTurnCommand handles the internal transition when an 8 ball goes into a sink in
 * billiards.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
@ExpectedParamNumber(value = 1, paramDescription = {"(int) game object ID of 8 ball"})
public class EightBallCommand implements Command {

  private final GameObject gameObject;

  /**
   * Constructs an instance of the EightBallCommand with the provided arguments.
   *
   * @param arguments One element, 8 ball GameObject id.
   * @param gameObjectMap a map from object ids to the actual GameObject
   */

  public EightBallCommand(List<Integer> arguments, Map<Integer, GameObject> gameObjectMap) {
    gameObject = gameObjectMap.get(arguments.get(0));

  }

  /**
   * Executes the command to handle actions related to the eight ball game object. This includes
   * hiding the eight ball, stopping all other game objects, and determining the winner.
   *
   * @param engine The game engine in which the command is executed.
   */

  @Override
  public void execute(GameEngine engine) {
    gameObject.setVisible(false);
    engine.getGameObjects().forEach(GameObject::stop);
    boolean didActiveWin = engine.getPlayerContainer().getActive().areAllScoreablesInvisible();
    engine.getPlayerContainer().getPlayers().forEach(p -> p.applyGameResult(
        (engine.getPlayerContainer().getActive().getId() == p.getId()) == didActiveWin));
  }
}
