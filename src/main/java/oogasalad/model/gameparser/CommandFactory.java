package oogasalad.model.gameparser;

import static oogasalad.model.gameparser.GameLoaderModel.BASE_PATH;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.api.exception.InvalidParameterNumberException;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.gameobject.GameObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CommandFactory {

  private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);
  private static final String COMMAND_PATH = "command.";

  public static Command createCommand(String cmdName, List<Integer> params,
      Map<Integer, GameObject> gameObjects)
      throws InvalidCommandException {
    try {
      Class<?> clazz = Class.forName(BASE_PATH + COMMAND_PATH + cmdName);
      Constructor<?> constructor = clazz.getConstructor(List.class, Map.class);
      ExpectedParamNumber annotation = constructor.getAnnotation(ExpectedParamNumber.class);

      System.out.println("is annotation null?: " + annotation);
      if (annotation != null) {
        int expectedParamNumber = annotation.value();
        System.out.println("command: " + cmdName);
        System.out.println("annotation param number: " + expectedParamNumber);
        if (params.size() < expectedParamNumber) {
          LOGGER.error("missing parameters for command " + cmdName);
          throw new InvalidParameterNumberException("Expected " + expectedParamNumber +
              " parameters for command " + cmdName + " but found " + params.size());
        }
      }
      return (Command) clazz.getDeclaredConstructor(List.class, Map.class)
          .newInstance(params, gameObjects);
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      LOGGER.error("command " + cmdName + " is invalid");
      throw new InvalidCommandException("invalid command");
    }
  }
}


