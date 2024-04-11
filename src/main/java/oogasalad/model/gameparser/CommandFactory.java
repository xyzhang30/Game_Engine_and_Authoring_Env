package oogasalad.model.gameparser;

import static oogasalad.model.gameparser.GameLoaderModel.BASE_PATH;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.gameengine.command.Command;

public class CommandFactory {

  private static final String COMMAND_PATH = "command.";
  public static Command createCommand(String cmdName, List<Double> params) throws InvalidCommandException {
    try {
      Class<?> clazz = Class.forName(BASE_PATH + COMMAND_PATH + cmdName);
      return (Command) clazz.getDeclaredConstructor(List.class).newInstance(params);
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      throw new InvalidCommandException("");
    }
  }
}


