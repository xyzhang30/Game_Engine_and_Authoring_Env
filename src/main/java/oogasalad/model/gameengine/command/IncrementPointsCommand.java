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
 * The IncrementPointsCommand class represents a command to add increment a Scoreable's temporary
 * score.
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
   * @param arguments Consists of one arguments: the ID of the GameObject to which the delayed
   *                  points should be incremented
   */

  @ExpectedParamNumber(value = 2, paramDescription = {"(double) game object ID to add",
      "(double) number of points to add"})
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
    optionalScoreable.ifPresent(scoreable -> scoreable.incrementTemporaryScore(arguments.get(1)));
  }

}
