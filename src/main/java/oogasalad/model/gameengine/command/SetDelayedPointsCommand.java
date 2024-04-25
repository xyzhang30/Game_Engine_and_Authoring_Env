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
 * The SetDelayedPointsCommand class represents a command to add delayed points to a Scoreable's
 * temporary score.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
@ExpectedParamNumber(value = 2, paramDescription = {"(double) game object ID",
    "(double) points to add"})
public class SetDelayedPointsCommand implements Command {

  private int newScore;
  private final GameObject gameObject;

  /**
   * Constructs an instance of the SetDelayedPointsCommand with the list of arguments determined
   * from the data file.
   *
   * @param arguments Consists of two arguments: the ID of the GameObject to which the delayed
   *                  points should be added, and the new amount of points for the Scoreable's
   *                  score
   * @param gameObjectMap a map from object ids to the actual GameObject
   */


  public SetDelayedPointsCommand(List<Integer> arguments, Map<Integer, GameObject> gameObjectMap) {
    gameObject = gameObjectMap.get(arguments.get(0));
    newScore = arguments.get(1);

  }

  /**
   * Executes the command to add delayed points to a Scoreable's temporary score. It retrieves the
   * GameObject corresponding to the provided ID from the game engine, then sets the temporary score
   * of the Scoreable (if the collidable provided is a scoreable) associated with the GameObject to
   * the specified amount of points.
   *
   * @param engine The game engine instance.
   */

  @Override
  public void execute(GameEngine engine) {
    Optional<Scoreable> optionalScoreable = gameObject.getScoreable();
    optionalScoreable.ifPresent(scoreable -> scoreable.setTemporaryScore(newScore));
  }

}
