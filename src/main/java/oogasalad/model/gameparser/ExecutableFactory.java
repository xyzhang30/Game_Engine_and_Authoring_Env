package oogasalad.model.gameparser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.api.exception.InvalidParameterNumberException;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.condition.Condition;
import oogasalad.model.gameengine.gameobject.GameObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExecutableFactory {

  private static final Logger LOGGER = LogManager.getLogger(ExecutableFactory.class);
  private static final String BASE_PATH = "oogasalad.model.gameengine.";
  private static final String CONDITION_PATH = "condition.";
  private static final String COMMAND_PATH = "command.";

  public static Condition createCondition(String conditionName, List<Integer> params,
      Map<Integer, GameObject> gameObjectMap)
      throws InvalidCommandException {
    return createInstance(BASE_PATH + CONDITION_PATH, conditionName, params, gameObjectMap);
  }

  public static Command createCommand(String commandName, List<Integer> params,
      Map<Integer, GameObject> gameObjectMap)
      throws InvalidCommandException {
    return createInstance(BASE_PATH + COMMAND_PATH, commandName, params, gameObjectMap);
  }

  private static <T> T createInstance(String path, String name, List<Integer> params,
      Map<Integer, GameObject> gameObjectMap)
      throws InvalidCommandException {
    try {
      Class<?> clazz = Class.forName(path + name);
      ExpectedParamNumber annotation = clazz.getAnnotation(ExpectedParamNumber.class);
      if (annotation != null) {
        int expectedParamNumber = annotation.value();
        if (params.size() != expectedParamNumber) {
          System.out.println(params);
          LOGGER.error("missing parameters for command/condition " + name);
          throw new InvalidParameterNumberException("Expected " + expectedParamNumber +
              " parameters for command/condition " + name + " but found " + params.size());
        }

      }

      Constructor<?> constructor = clazz.getDeclaredConstructor(List.class, Map.class);
      constructor.setAccessible(true); // Ensure private constructors can be accessed
      return (T) constructor.newInstance(params, gameObjectMap);
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      LOGGER.error("Command/Condition " + name + " is invalid");
      throw new InvalidCommandException("invalid command/condition");
    }
  }
}
