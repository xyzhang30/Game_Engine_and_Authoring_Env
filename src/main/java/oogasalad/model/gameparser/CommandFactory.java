package oogasalad.model.gameparser;

import static oogasalad.model.gameparser.GameLoaderModel.BASE_PATH;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.gameengine.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandFactory {

  private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);
  private static final String COMMAND_PATH = "command.";

  public static Command createCommand(String cmdName, List<Double> params)
      throws InvalidCommandException {
    try {
      Class<?> clazz = Class.forName(BASE_PATH + COMMAND_PATH + cmdName);
      return (Command) clazz.getDeclaredConstructor(List.class).newInstance(params);
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      LOGGER.error("command " + cmdName + " is invalid");
      throw new InvalidCommandException("invalid command");
    }
  }
}


