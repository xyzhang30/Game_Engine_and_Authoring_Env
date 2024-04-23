package oogasalad.model.gameengine.command;

import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.player.Player;

/**
 * Represents a command to handle actions related to an eight ball game object.
 * This command is responsible for hiding the eight ball object and stopping all other game objects.
 *
 * @author Noah Loewy
 */

public class EightBallCommand implements Command {

  private final List<Integer> arguments;
  private final GameObject gameObject;

  /**
   * Constructs an EightBallCommand with the specified arguments and game object map.
   *
   * @param arguments    The list of arguments for the command.
   * @param gameObjectMap    The map of game objects.
   */

  @ExpectedParamNumber(value = 1, paramDescription = {"game object ID of 8 ball"})
  public EightBallCommand(List<Integer> arguments, Map<Integer, GameObject> gameObjectMap) {
    this.arguments = arguments;
    gameObject = gameObjectMap.get(arguments.get(0));

  }

  /**
   * Executes the command to handle actions related to the eight ball game object.
   * This includes hiding the eight ball, stopping all other game objects, and determining the winner.
   *
   * @param engine    The game engine in which the command is executed.
   */

  @Override
  public void execute(GameEngine engine) {
    gameObject.setVisible(false);
    engine.getGameObjects().forEach(GameObject::stop);
    boolean didActiveWin = engine.getPlayerContainer().getActive().areAllScoreablesInvisible();
    engine.getPlayerContainer().getPlayers().forEach(p->p.applyGameResult(
        engine.getPlayerContainer().getActive().getId() == (p.getId()) == didActiveWin));
  }
}
