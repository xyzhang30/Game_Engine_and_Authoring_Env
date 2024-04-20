package oogasalad.model.gameengine.command;

import java.util.List;
import java.util.Optional;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;

/**
 * The AddDelayedPointsCommand class represents a command to add delayed points to a Scoreable's
 * temporary score.
 *
 * @author Noah Loewy
 */

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class IncrementPointsCommand implements Command {

  private final List<Double> arguments;

  /**
   * Constructs an instance of the AddDelayedPointsCommand with the list of arguments determined
   * from the data file.
   *
   * @param arguments Consists of two arguments: the ID of the GameObject to which the delayed
   *                  points should be added, and the amount of points to add to the Scoreable's
   *                  score
   */

  @ExpectedParamNumber(value = 1, paramDescription = {"(double) game object ID to add"})
  public IncrementPointsCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  /**
   * Executes the command to increment a Scoreable's temporary score by 1
   *
   * @param engine The game engine instance.
   */

  @Override
  public void execute(GameEngine engine) {
    Optional<Scoreable> optionalScoreable =
        engine.getGameObjectContainer().getGameObject((int) Math.round(arguments.get(0))).getScoreable();
    optionalScoreable.ifPresent(Scoreable::incrementTemporaryScore);
  }

}

