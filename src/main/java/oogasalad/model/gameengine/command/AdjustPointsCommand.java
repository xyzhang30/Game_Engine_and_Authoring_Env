package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.api.exception.InvalidParameterNumberException;
import oogasalad.model.gameengine.GameEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdjustPointsCommand extends AdjustPointsGivenPlayerCommand {

  private static final Logger LOGGER = LogManager.getLogger(AdjustPointsCommand.class);
  private final List<Double> arguments;

  public AdjustPointsCommand(List<Double> arguments) {
    super(arguments);
    this.arguments = arguments;
  }

  @Override
  public void execute(GameEngine engine) throws InvalidParameterNumberException {
    try {
      adjust(engine, (int) Math.round(arguments.get(1)));
    } catch (IndexOutOfBoundsException e){
      LOGGER.error("Incorrect number of command parameters");
      throw new InvalidParameterNumberException("Incorrect number of command parameters");
    }
  }
}

//Backlog
//Currently Parameters to Commands required to be defined at Authoring Time
//Runtime is ideal
/**
 * (FIX): Directional Collisions ==> But what if other things
 */