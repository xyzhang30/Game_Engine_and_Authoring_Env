package oogasalad.model.gameengine.command;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;

/**
 * The IncrementPointsCommand class represents a command to add increment a Scoreable's temporary
 * score.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
@ExpectedParamNumber(value = 2, paramDescription = {"(int) game object ID to add",
    "(int) number of points to add"})
public class IncrementPointsCommand implements Command {

  private int increment;
  private final GameObject gameObject;

  /**
   * Constructs an instance of the SetDelayedPointsCommand with the list of arguments determined
   * from the data file.
   *
   * @param arguments Consists of one argument: the ID of the GameObject to which the delayed points
   *                  should be incremented
   */

  public IncrementPointsCommand(List<Integer> arguments, Map<Integer, GameObject> gameObjectMap) {
    increment = arguments.get(1);
    gameObject = gameObjectMap.get(arguments.get(0));
  }

  /**
   * Executes the command to increment a Scoreable's temporary score by a specified amount
   *
   * @param engine The game engine instance.
   */

  @Override
  public void execute(GameEngine engine) {
    Optional<Scoreable> optionalScoreable = gameObject.getScoreable();
    optionalScoreable.ifPresent(scoreable -> scoreable.incrementTemporaryScore(increment));
  }
}
